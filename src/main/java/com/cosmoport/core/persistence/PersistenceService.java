package com.cosmoport.core.persistence;

import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import com.cosmoport.core.persistence.exception.ValidationException;
import com.cosmoport.core.persistence.param.QueryParam;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PersistenceService<T> {
    private final Provider<DataSource> ds;
    private final Logger logger;

    private final boolean keepConnection;

    @Inject
    protected PersistenceService(Logger logger, Provider<DataSource> ds) {
        this.ds = ds;
        this.logger = logger;
        this.keepConnection = false;
    }

    Connection getConnection() throws SQLException {
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

    Optional<T> findById(final String sql, final long id) {
        T result = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            rs = statement.executeQuery();

            if (rs.next()) {
                result = map(rs);
            }
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(rs, statement, conn);
        }

        return result != null ? Optional.of(result) : Optional.empty();
    }

    Optional<T> findByParam(final String sql, final String param) {
        T result = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            statement = conn.prepareStatement(sql);
            statement.setString(1, param);
            rs = statement.executeQuery();

            if (rs.next()) {
                result = map(rs);
            }
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(rs, statement, conn);
        }

        return result != null ? Optional.of(result) : Optional.empty();
    }

    /*protected long insertStringById(final String sql, final String value, final long id)
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
    }*/

    int deleteByIdWithParams(final String sql, final long id, final QueryParam... params) {
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
        if (param.type().equals("long")) {
            statement.setLong(i, (Long) param.value());
        } else {
            if (param.type().equals("string")) {
                statement.setString(i, (String) param.value());
            }
        }
    }

    protected abstract T map(final ResultSet rs) throws SQLException;

    void throwServerApiException(Throwable t) {
        logger.error(t.getMessage());
        throw new RuntimeException(t.getMessage());
    }

    private void close(AutoCloseable resource) {
        if (resource == null) {
            return;
        }

        try {
            resource.close();
        } catch (Exception e) {
            logger.error("Couldn't close resource, " + e.getMessage());
        }
    }

    void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource instanceof Connection) {
                closeConnection((Connection) resource);
            } else {
                close(resource);
            }
        }
    }

    private void closeConnection(Connection conn) {
        if (!keepConnection) {
            close(conn);
        }
    }

    void rollback(Connection conn) {
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
    private static boolean hasConstrainViolation(SQLException e) {
        return
                // SQLite
                e.getErrorCode() == 19 ||
                        // H2
                        e.getErrorCode() == 23505 ||
                        // ORACLE ???
                        e.getErrorCode() == 23000;
    }

    static void throwConstrainViolation(final SQLException e) {
        if (hasConstrainViolation(e)) {
            Matcher m = Pattern
                    .compile("\\((UNIQUE|CHECK) constraint failed: (.*?)\\)")
                    .matcher(e.getMessage());
            if (m.find()) {
                final String name = m.group(2);

                if ("UNIQUE".equals(m.group(1))) {
                    throw new UniqueConstraintException(name);
                } else {
                    if ("CHECK".equals(m.group(1))) {
                        throw new ValidationException(name);
                    }
                }
            } else {
                // Try to parse a trigger exception
                final Matcher m2 = Pattern.compile(
                        "\\[SQLITE_CONSTRAINT_TRIGGER].*?\\((.*)\\)$")
                        .matcher(e.getMessage());
                if (m2.find()) {
                    throw new ValidationException(m2.group(1));
                }
            }
        }
    }

    public Logger getLogger() {
        return logger;
    }
}
