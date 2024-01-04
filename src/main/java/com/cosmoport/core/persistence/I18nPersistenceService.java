package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.I18nDto;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * I18N database entities service.
 *
 * @since 0.1.0
 */
public final class I18nPersistenceService extends PersistenceService<I18nDto> {
    @Inject
    I18nPersistenceService(Logger logger, Provider<DataSource> ds) {
        super(logger, ds);
    }

    /**
     * Finds a record by its tag value.
     *
     * @param tag A tag value to find with.
     * @return Optional {@code I18nDto} record.
     */
    Optional<I18nDto> findByTag(final String tag) {
        final List<I18nDto> result = getAllByParams("SELECT * FROM I18N WHERE tag = ?", tag);

        return !result.isEmpty() ? Optional.of(result.get(0)) : Optional.empty();
    }

    public Optional<I18nDto> getById(final long id) {
        return findById("SELECT * FROM I18N WHERE id = ?", id);
    }

    I18nDto save(I18nDto i18n, final Connection extConn) throws RuntimeException {
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = extConn != null ? extConn : getConnection();

            statement = conn.prepareStatement(
                    "INSERT INTO I18N (tag, external, description, params) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, i18n.getTag());
            statement.setBoolean(2, i18n.isExternal());
            statement.setString(3, i18n.getDescription());
            statement.setString(4, i18n.getParams());

            if (statement.executeUpdate() < 0) {
                throw new Exception();
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    i18n.setId(generatedKeys.getLong(1));
                } else {
                    throw new Exception();
                }
            }
        } catch (SQLException sqlexception) {
            throwConstrainViolation(sqlexception);
            throwServerApiException(sqlexception);
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(statement);
            if (extConn == null) {
                close(conn);
            }
        }

        return i18n;
    }

    @Override
    protected I18nDto map(final ResultSet rs) throws SQLException {
        return mapRs(rs, "");
    }

    private I18nDto mapRs(final ResultSet rs, final String idNameOverride) throws SQLException {
        return new I18nDto(
                idNameOverride.isEmpty() ? rs.getLong("id") : rs.getLong(idNameOverride),
                rs.getString("tag"),
                rs.getBoolean("external"),
                rs.getString("description"),
                rs.getString("params")
        );
    }

    I18nDto mapObject(final ResultSet rs, final String idNameOverride) throws SQLException {
        return mapRs(rs, idNameOverride);
    }
}
