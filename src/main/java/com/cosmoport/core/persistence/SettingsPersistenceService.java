package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.SettingsDto;
import com.cosmoport.core.dto.SyncServerParamsDto;
import com.cosmoport.core.dto.SyncServerParamsDtoBuilder;
import com.cosmoport.core.persistence.constant.Constants;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class SettingsPersistenceService extends PersistenceService<SettingsDto> {
    public SettingsPersistenceService(Logger logger, DataSource ds) {
        super(logger, ds);
    }

    @Override
    protected SettingsDto map(ResultSet rs) throws SQLException {
        return new SettingsDto(rs.getLong("id"), rs.getString("param"), rs.getString("value"));
    }

    public List<SettingsDto> getAll() {
        return getAll("SELECT * FROM SETTINGS");
    }

    public List<SettingsDto> getAllWithoutProtectedValues() {
        return getAll("SELECT * FROM SETTINGS WHERE param NOT IN ('" + Constants.password + "', '" +
                Constants.syncServerKey + "')");
    }

    public SyncServerParamsDto getSyncServerParams() {
        List<SettingsDto> all = getAll("SELECT * FROM SETTINGS WHERE param LIKE 'sync_server_%'");

        SyncServerParamsDtoBuilder builder = new SyncServerParamsDtoBuilder();
        all.forEach(p -> {
            switch (p.getParam()) {
                case Constants.syncServerAddress:
                    builder.setSyncServerAddress(p.getValue());
                    break;
                case Constants.syncServerKey:
                    builder.setSyncServerKey(p.getValue());
                    break;
                case Constants.syncServerOn:
                    builder.setSyncServerOn(p.getValue());
            }
        });

        return builder.createSyncServerParamsDto();
    }

    int getPreEventPeriod() {
        int result = 0;

        final Optional<SettingsDto> obj = findByParam(
                "SELECT * FROM SETTINGS WHERE param = ?",
                Constants.preBoardingPeriod
        );

        if (obj.isPresent()) {
            result = Integer.parseInt(obj.get().getValue());
        }

        return result;
    }

    public boolean paramEquals(final String param, final String value) {
        final Optional<SettingsDto> obj = findByParam("SELECT * FROM SETTINGS WHERE param = ?", param);

        return obj.isPresent() && obj.get().getValue().equals(value);
    }

    public boolean updateSettingForId(final long id, final String value) throws RuntimeException {
        boolean result;

        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();

            statement = conn.prepareStatement("UPDATE SETTINGS SET value = ? WHERE id = ?");
            statement.setString(1, value);
            statement.setLong(2, id);

            result = statement.executeUpdate() == 1;
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException();
        } finally {
            close(statement, conn);
        }

        return result;
    }

    public boolean updateSettingForParam(final String param, final String value) throws RuntimeException {
        boolean result;

        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();

            statement = conn.prepareStatement("UPDATE SETTINGS SET value = ? WHERE param = ?");
            statement.setString(1, value);
            statement.setString(2, param);

            result = statement.executeUpdate() == 1;
        } catch (Exception e) {
            getLogger().error(e.getMessage());
            throw new RuntimeException();
        } finally {
            close(statement, conn);
        }

        return result;
    }
}
