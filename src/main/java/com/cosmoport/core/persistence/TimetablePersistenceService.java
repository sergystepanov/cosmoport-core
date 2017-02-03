package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.TimetableDto;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Timetable database entity service.
 *
 * @since 0.1.0
 */
public class TimetablePersistenceService extends PersistenceService<TimetableDto> {
    protected TimetablePersistenceService(Logger logger, Provider<DataSource> ds) {
        super(logger, ds);
    }

    public List<TimetableDto> getAll() {
        return getAll("SELECT * FROM TIMETABLE");
    }

    private void validate(final TimetableDto record) {

    }

    public long save(final TimetableDto record) throws Exception {
        validate(record);

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
            if (isUniqueViolation(sqlexception)) {
                throw new UniqueConstraintException(uniqueId);
            }
            throwServerApiException(sqlexception);
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(statement, conn);
        }

        return newId;
    }

    @Override
    protected TimetableDto map(ResultSet rs) throws SQLException {
        return new TimetableDto(
                rs.getLong("id"),
                rs.getString("event_date"),
                rs.getLong("event_type_id"),
                rs.getLong("event_status_id"),
                rs.getLong("event_destination_id"),
                rs.getLong("gate_id"),
                rs.getLong("start_time"),
                rs.getLong("duration_time"),
                rs.getLong("repeat_interval"),
                rs.getDouble("cost"),
                rs.getLong("people_limit"),
                rs.getLong("contestants"),
                rs.getString("date_added")
        );
    }
}
