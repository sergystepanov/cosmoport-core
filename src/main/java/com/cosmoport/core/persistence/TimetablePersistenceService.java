package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventDto;
import com.cosmoport.core.persistence.exception.ValidationException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Timetable database entity service.
 *
 * @since 0.1.0
 */
public class TimetablePersistenceService extends PersistenceService<EventDto> {
    @Inject
    TimetablePersistenceService(Logger logger, Provider<DataSource> ds) {
        super(logger, ds);
    }

    public List<EventDto> getAll() {
        return getAll("SELECT * FROM TIMETABLE");
    }

    /**
     * Fetches all events from the table.
     * Uses hardcoded params, oh well.
     *
     * @param date   null or the date string to filter with formatted in yyyy-mm-dd supposedly.
     * @param gateId null or the id number to filter by a gate id.
     * @return A collection of {@code TimetableDto} objects or an empty array.
     * @since 0.1.0
     */
    public List<EventDto> getAllWithFilter(final String date, final Long gateId) {
        getLogger().debug("date={}, gateId={}", date, gateId);

        final boolean hasDate = date != null && !date.equals("");
        final boolean hasGate = gateId != null && gateId != 0;
        final boolean hasParams = hasDate || hasGate;

        final List<Object> params = new ArrayList<>();
        if (hasDate) {
            params.add(date);
        }
        if (hasGate) {
            params.add(gateId);
        }

        final StringBuilder sql = new StringBuilder("SELECT * FROM TIMETABLE");
        if (hasParams) {
            sql.append(" WHERE ");
            sql.append(hasDate && hasGate ? "event_date = ? AND gate_id = ?" :
                    hasDate ? "event_date = ?" : "gate_id = ?");
        }
        getLogger().debug("sql={}", sql.toString());

        return getAllByParams(sql.toString(), params.toArray());
    }

    private void validate(final EventDto event) throws ValidationException {
        final List<Object> params = new ArrayList<>();
        params.add(event.getEventDate());
        params.add(event.getStartTime());
        params.add(event.getEventDate());
        params.add(event.getStartTime() + event.getDurationTime());

        final List<EventDto> overlapping = getAllByParams(
                "SELECT * FROM TIMETABLE WHERE (event_date = ? AND start_time = ?)" +
                        " OR (event_date = ? AND start_time + duration_time = ?)",
                params.toArray());

        if (overlapping.size() > 0) {
            String divider = "";
            StringBuilder message = new StringBuilder();
            message.append("Overlapping events: ");

            for (final EventDto event0 : overlapping) {
                message
                        .append(divider)
                        .append("[")
                        .append(event0.getId())
                        .append("] start: ")
                        .append(event0.getStartTime())
                        .append(", end: ")
                        .append(event0.getStartTime() + event0.getDurationTime());
                divider = " | ";
            }

            throw new ValidationException(message.toString());
        }
    }

    public long save(final EventDto record) {
        validate(record);

        long newId = 0;
//        Connection conn = null;
//        PreparedStatement statement = null;
//
//        final String uniqueId = "event001";
//
//        try {
//            conn = getConnection();
//
//            final String sql = "INSERT INTO TIMETABLE (departure_time, type, duration, destination, cost, status," +
//                    " gate_no, passengers_max, bought) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            statement.setInt(1, 111);
//            statement.setString(2, "text/text");
//            statement.setInt(3, 222);
//            statement.setString(4, "sedna");
//            statement.setDouble(5, 33.33);
//            statement.setString(6, "pending");
//            statement.setInt(7, 1);
//            statement.setInt(8, 100);
//            statement.setInt(9, 10);
//
//            if (statement.executeUpdate() < 0) {
//                throw new Exception();
//            }
//
//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    newId = generatedKeys.getLong(1);
//                } else {
//                    throw new Exception();
//                }
//            }
//        } catch (SQLException sqlexception) {
//            if (isUniqueViolation(sqlexception)) {
//                throw new UniqueConstraintException(uniqueId);
//            }
//            throwServerApiException(sqlexception);
//        } catch (Exception e) {
//            throwServerApiException(e);
//        } finally {
//            close(statement, conn);
//        }

        return newId;
    }

    @Override
    protected EventDto map(ResultSet rs) throws SQLException {
        return new EventDto(
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
