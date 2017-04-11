package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventDto;
import com.cosmoport.core.persistence.exception.ValidationException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Timetable database entity service.
 *
 * @since 0.1.0
 */
public class TimetablePersistenceService extends PersistenceService<EventDto> {
    @Inject
    public TimetablePersistenceService(Logger logger, Provider<DataSource> ds) {
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
            sql.append(hasDate && hasGate ? "event_date = ? AND gate_id = ?" : hasDate ? "event_date = ?" : "gate_id = ?");
        }
        getLogger().debug("sql={}", sql.toString());

        return getAllByParams(sql.toString(), params.toArray());
    }

    private void validate(final EventDto event) throws ValidationException {
        final Object[] params = {event.getGateId(), event.getEventDate(), event.getStartTime(), event.getEventDate(),
                event.getStartTime() + event.getDurationTime()};

        final List<EventDto> overlapping = getAllByParams(
                "SELECT * FROM TIMETABLE WHERE gate_id = ? AND ((event_date = ? AND start_time = ?)" +
                        " OR (event_date = ? AND start_time + duration_time = ?))",
                params);

        if (overlapping.size() > 0) {
            String divider = "";
            StringBuilder message = new StringBuilder();
            message.append("Overlapping events: ");

            for (final EventDto event0 : overlapping) {
                message
                        .append(divider)
                        .append("[gate: ")
                        .append(event0.getGateId())
                        .append("] start: ")
                        .append(event0.getStartTime())
                        .append(", end: ")
                        .append(event0.getStartTime() + event0.getDurationTime());
                divider = " | ";
            }

            throw new ValidationException(message.toString());
        }
    }

    public EventDto save(final EventDto record) {
        validate(record);

        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();

            statement = conn.prepareStatement(
                    "INSERT INTO TIMETABLE (event_date, event_type_id, event_status_id, event_destination_id, " +
                            "gate_id, start_time, duration_time, repeat_interval, cost, people_limit, contestants) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, record.getEventDate());
            statement.setLong(2, record.getEventTypeId());
            statement.setLong(3, record.getEventStatusId());
            statement.setLong(4, record.getEventDestinationId());
            statement.setLong(5, record.getGateId());
            statement.setLong(6, record.getStartTime());
            statement.setLong(7, record.getDurationTime());
            statement.setLong(8, record.getRepeatInterval());
            statement.setDouble(9, record.getCost());
            statement.setLong(10, record.getPeopleLimit());
            statement.setLong(11, record.getContestants());

            if (statement.executeUpdate() < 0) {
                throw new Exception();
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    record.setId(generatedKeys.getLong(1));
                } else {
                    throw new Exception();
                }
            }
        } catch (SQLException sqlexception) {
            throwConstrainViolation(sqlexception);
            // if not than
            throwServerApiException(sqlexception);
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(statement, conn);
        }

        return record;
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
