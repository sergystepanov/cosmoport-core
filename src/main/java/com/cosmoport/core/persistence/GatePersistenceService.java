package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.GateDto;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class GatePersistenceService extends PersistenceService<GateDto> {
    @Inject
    protected GatePersistenceService(Logger logger, Provider<DataSource> provider) {
        super(logger, provider);
    }

    public List<GateDto> getAll() {
        return getAll("SELECT * FROM GATE");
    }

    @Override
    protected GateDto map(final ResultSet rs) throws SQLException {
        return new GateDto(rs.getLong("id"), rs.getInt("number"), rs.getString("gate_name"));
    }
}
