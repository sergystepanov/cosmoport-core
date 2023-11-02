package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.LocaleDto;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Locale entity database persistence service.
 *
 * @since 0.1.0
 */
public final class LocalePersistenceService extends PersistenceService<LocaleDto> {
    @Inject
    LocalePersistenceService(Logger logger, Provider<DataSource> ds) {
        super(logger, ds);
    }

    @Override
    protected LocaleDto map(final ResultSet rs) throws SQLException {
        return new LocaleDto(
                rs.getLong("id"),
                rs.getString("code"),
                rs.getBoolean("is_default"),
                rs.getString("locale_description"),
                rs.getBoolean("show"),
                rs.getInt("show_time"));
    }

    public List<LocaleDto> getAll() {
        return getAll("SELECT * FROM LOCALE");
    }

    public List<LocaleDto> getAllVisible() {
        return getAll("SELECT * FROM LOCALE WHERE show = 1");
    }

    public LocaleDto createLocale(final LocaleDto locale) throws UniqueConstraintException {
        LocaleDto newLocale = null;
        long newId;
        Connection conn = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            statement = conn.prepareStatement(
                    "INSERT INTO LOCALE(code, is_default, locale_description) VALUES (?, 0, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, locale.getCode());
            statement.setString(2, locale.getLocaleDescription());

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

            // Copy all locale id=1 values into new locale as defaults
            statement2 = conn.prepareStatement(
                    "INSERT INTO TRANSLATION (i18n_id, locale_id, tr_text) " +
                            "SELECT i18n_id, ? AS locale_id, tr_text FROM TRANSLATION WHERE locale_id = 1");
            statement2.setLong(1, newId);
            if (statement2.executeUpdate() < 0) {
                throw new Exception();
            }

            newLocale = new LocaleDto(newId, locale.getCode(), false, locale.getLocaleDescription(), false, 1);

            conn.commit();
        } catch (SQLException sqlexception) {
            rollback(conn);
            throwConstrainViolation(sqlexception);
            throwServerApiException(sqlexception);
        } catch (Exception e) {
            rollback(conn);
            throwServerApiException(e);
        } finally {
            close(statement, statement2, conn);
        }

        return newLocale;
    }

    /**
     * Updates locale show data.
     *
     * @param locale The locale to change data.
     * @return Changed locale.
     */
    public LocaleDto updateLocaleShowData(final LocaleDto locale) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = getConnection();

            statement = conn.prepareStatement("UPDATE LOCALE SET show = ?, show_time = ? WHERE id = ?");
            statement.setBoolean(1, locale.isShow());
            statement.setInt(2, locale.getShowTime());
            statement.setLong(3, locale.getId());

            if (statement.executeUpdate() < 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(statement, conn);
        }

        return locale;
    }

    Optional<LocaleDto> findById(final long id) {
        return findById("SELECT * FROM LOCALE WHERE id = ?", id);
    }
}
