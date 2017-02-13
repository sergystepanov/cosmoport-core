package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.I18nDto;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * I18N database entities service.
 *
 * @since 0.1.0
 */
public final class I18nPersistenceService extends PersistenceService<I18nDto> {
    @Inject
    protected I18nPersistenceService(Logger logger, Provider<DataSource> ds) {
        super(logger, ds);
    }

    @Override
    protected I18nDto map(final ResultSet rs) throws SQLException {
        return mapRs(rs, "");
    }

    private I18nDto mapRs(final ResultSet rs, final String idNameOverride) throws SQLException {
        return new I18nDto(
                idNameOverride.equals("") ? rs.getLong("id") : rs.getLong(idNameOverride),
                rs.getString("tag"),
                rs.getBoolean("external"),
                rs.getString("description"),
                rs.getString("params")
        );
    }

    public I18nDto mapObject(final ResultSet rs, final String idNameOverride) throws SQLException {
        return mapRs(rs, idNameOverride);
    }
}
