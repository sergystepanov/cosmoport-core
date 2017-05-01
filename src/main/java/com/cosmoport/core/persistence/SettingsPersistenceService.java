package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.SettingsDto;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SettingsPersistenceService extends PersistenceService<SettingsDto> {
    @Inject
    protected SettingsPersistenceService(Logger logger, Provider<DataSource> ds) {
        super(logger, ds);
    }

    @Override
    protected SettingsDto map(ResultSet rs) throws SQLException {
        return new SettingsDto(rs.getLong("id"), rs.getString("param"), rs.getString("value"));
    }

    public List<SettingsDto> getAll() {
        return getAll("SELECT * FROM SETTINGS");
    }
}
