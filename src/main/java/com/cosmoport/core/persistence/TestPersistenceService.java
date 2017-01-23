package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.TestDto;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TestPersistenceService extends PersistenceService<TestDto> {
    @Inject
    public TestPersistenceService(Logger logger, Provider<DataSource> ds) {
        super(logger, ds);
    }

    @Override
    protected TestDto map(final ResultSet rs) throws SQLException {
        return new TestDto(
                rs.getLong("id"),
                rs.getInt("departure_time"),
                rs.getString("type"),
                rs.getInt("duration"),
                rs.getString("destination"),
                rs.getDouble("cost"),
                rs.getString("status"),
                rs.getInt("gate_no"),
                rs.getInt("passengers_max"),
                rs.getInt("bought"),
                rs.getInt("date_added"));
    }

    public List<TestDto> getAll() {
        return getAll("SELECT * FROM TIMETABLE");
    }
}
