package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.LocaleDto;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Locale entity database persistence service.
 *
 * @since 0.1.0
 */
public final class LocalePersistenceService extends PersistenceService<LocaleDto> {
    @Inject
    protected LocalePersistenceService(Logger logger, Provider<DataSource> ds) {
        super(logger, ds);
    }

    @Override
    protected LocaleDto map(final ResultSet rs) throws SQLException {
        return new LocaleDto(
                rs.getLong("id"),
                rs.getString("code"),
                rs.getBoolean("is_default"),
                rs.getString("locale_description")
        );
    }

    public List<LocaleDto> getAll() {
        return getAll("SELECT * FROM LOCALE");
    }

    public LocaleDto createLocale(final LocaleDto locale) throws UniqueConstraintException {
        LocaleDto newLocale = null;
        long newId;
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = getConnection();

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

            newLocale = new LocaleDto(newId, locale.getCode(), false, locale.getLocaleDescription());
        } catch (SQLException sqlexception) {
            if (isUniqueViolation(sqlexception)) {
                throw new UniqueConstraintException(locale.getCode());
            }
            throwServerApiException(sqlexception);
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(statement, conn);
        }

        return newLocale;
    }
}
