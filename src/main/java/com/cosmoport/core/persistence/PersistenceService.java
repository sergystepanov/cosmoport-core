package com.cosmoport.core.persistence;

import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import com.cosmoport.core.persistence.param.QueryParam;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PersistenceService<T> {
    private final Provider<DataSource> ds;
    private final Logger logger;

    private boolean keepConnection;

    private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {
    };

    @Inject
    protected PersistenceService(Logger logger, Provider<DataSource> ds) {
        this.ds = ds;
        this.logger = logger;
        this.keepConnection = false;
    }

    protected Connection getConnection() throws SQLException {
        return ds.get().getConnection();
    }

    List<T> getAll(final String sql) {
        List<T> objects = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            statement = conn.createStatement();
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                objects.add(map(rs));
            }
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(rs, statement, conn);
        }

        return objects;
    }

    List<T> getAllByParams(final String sql, final Object... params) {
        List<T> values = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            statement = conn.prepareStatement(sql);

            byte index = 1;
            for (Object param : params) {
                if (param instanceof String) {
                    statement.setString(index++, (String) param);
                } else {
                    if (param instanceof Long) {
                        statement.setLong(index++, (Long) param);
                    } else {
                        if (param instanceof Integer) {
                            statement.setInt(index++, (Integer) param);
                        }
                    }
                }
            }

            rs = statement.executeQuery();

            while (rs.next()) {
                values.add(map(rs));
            }
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(rs, statement, conn);
        }

        return values;
    }

    protected long insertStringById(final String sql, final String value, final long id)
            throws UniqueConstraintException {
        long newId = 0;
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = getConnection();

            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, value);
            statement.setLong(2, id);

            if (statement.executeUpdate() < 0) {
                throw new Exception();
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newId = generatedKeys.getLong(1);
                } else {
                    throw new Exception();
                }
            }
        } catch (SQLException sqlexception) {
            if (isUniqueViolation(sqlexception)) {
                throw new UniqueConstraintException(value);
            }
            throwServerApiException(sqlexception);
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(statement, conn);
        }

        return newId;
    }

    protected int deleteByIdWithParams(final String sql, final long id, final QueryParam... params) {
        int deleted = 0;

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = getConnection();

            statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            int i = 2;
            for (QueryParam param : params) {
                setStatementParam(statement, i++, param);
            }

            deleted = statement.executeUpdate();
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(statement, conn);
        }

        return deleted;
    }

    private void setStatementParam(final PreparedStatement statement,
                                   int i, final QueryParam param) throws SQLException {
        if (param.getType().equals("long")) {
            statement.setLong(i, (Long) param.getValue());
        } else {
            if (param.getType().equals("string")) {
                statement.setString(i, (String) param.getValue());
            }
        }
    }

    protected abstract T map(final ResultSet rs) throws SQLException;

    protected void throwServerApiException(Throwable t) {
        logger.error(t.getMessage());
        throw new RuntimeException();
    }

    private T create() {
        try {
            // Using the raw type Class object for the token to
            // get at its default constructor.
            return typeToken.constructor(typeToken.getRawType().getConstructor()).invoke(null);
        } catch (Exception e) {
            logger.error("Couldn't create generic class, " + e.getMessage());
        }

        return null;
    }

    public void close(AutoCloseable resource) {
        if (resource == null) {
            return;
        }

        try {
            resource.close();
        } catch (Exception e) {
            logger.error("Couldn't close resource, " + e.getMessage());
        }
    }

    public void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource instanceof Connection) {
                closeConnection((Connection) resource);
            } else {
                close(resource);
            }
        }
    }

    protected void closeConnection(Connection conn) {
        if (!keepConnection) {
            close(conn);
        }
    }

    protected void closeStatement(Statement s) {
        close(s);
    }

    protected void closeResultSet(ResultSet rs) {
        close(rs);
    }

    protected void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                logger.error("Couldn't rollback the operations, " + e.getMessage());
            }
        }
    }

    /**
     * Checks on duplicate key. ! Database dependent
     */
    public static boolean isUniqueViolation(SQLException e) {
        return
                // SQLite
                e.getErrorCode() == 19 ||
                        // H2
                        e.getErrorCode() == 23505 ||
                        // ORACLE ???
                        e.getErrorCode() == 23000;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setKeepConnection(boolean keepConnection_) {
        keepConnection = keepConnection_;
    }
}
