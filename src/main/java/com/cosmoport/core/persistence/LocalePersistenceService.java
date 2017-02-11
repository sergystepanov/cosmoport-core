package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.LocaleDto;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
