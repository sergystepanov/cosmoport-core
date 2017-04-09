package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.TestDto;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
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

    public void remove(final long id) {
        deleteByIdWithParams("DELETE FROM TIMETABLE WHERE id = ?", id);
    }

    public long save() throws Exception {
        long newId = 0;
        Connection conn = null;
        PreparedStatement statement = null;

        final String uniqueId = "event001";

        try {
            conn = getConnection();

            final String sql = "INSERT INTO TIMETABLE (departure_time, type, duration, destination, cost, status," +
                    " gate_no, passengers_max, bought) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, 111);
            statement.setString(2, "text/text");
            statement.setInt(3, 222);
            statement.setString(4, "sedna");
            statement.setDouble(5, 33.33);
            statement.setString(6, "pending");
            statement.setInt(7, 1);
            statement.setInt(8, 100);
            statement.setInt(9, 10);

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
            throwConstrainViolation(sqlexception);
            throwServerApiException(sqlexception);
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(statement, conn);
        }

        return newId;
    }
}
