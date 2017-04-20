package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventTypeDto;
import com.cosmoport.core.dto.request.CreateEventTypeRequestDto;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import com.cosmoport.core.persistence.exception.ValidationException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Event type entity database service.
 *
 * @since 0.1.0
 */
public final class EventTypePersistenceService extends PersistenceService<EventTypeDto> {
    @Inject
    EventTypePersistenceService(Logger logger, Provider<DataSource> ds) {
        super(logger, ds);
    }

    @Override
    protected EventTypeDto map(final ResultSet rs) throws SQLException {
        return new EventTypeDto(
                rs.getLong("id"),
                rs.getLong("i18n_event_type_name"),
                rs.getLong("i18n_event_type_subname"),
                rs.getLong("i18n_event_type_description"),
                rs.getInt("default_duration"),
                rs.getInt("default_repeat_interval")
        );
    }

    public List<EventTypeDto> getAll() {
        return getAll("SELECT * FROM EVENT_TYPE");
    }

    /**
     * Validates an event type object.
     * <p>
     * There's shouldn't be two events types ith:
     * - the same name and subname
     * </p>
     *
     * @param eventType The event type to validate.
     * @throws ValidationException In case of violation of a constraint.
     * @since 0.1.0
     */
    private void validate(final CreateEventTypeRequestDto eventType) throws ValidationException {
        final Object[] params = {eventType.getName(), eventType.getSubname()};

        // TODO Be aware of default translation, mate
        // TODO Trim

        final List<EventTypeDto> duplicates = getAllByParams(
                "SELECT et.* FROM EVENT_TYPE et " +
                        "LEFT JOIN TRANSLATION t0 ON et.i18n_event_type_name = t0.i18n_id AND t0.locale_id = 1 " +
                        "LEFT JOIN TRANSLATION t1 ON et.i18n_event_type_subname = t1.i18n_id AND t1.locale_id = 1 " +
                        "WHERE t0.tr_text = ? AND t1.tr_text = ? " +
                        "LIMIT 1",
                params);

        if (duplicates.size() > 0) {
            throw new ValidationException("Duplicate with " + eventType.getName() + ", " + eventType.getSubname());
        }
    }

    /**
     * Saves new event type record.
     *
     * @param eventType The record to save.
     * @return A new record with id.
     * @throws UniqueConstraintException In case of unique constraint violation during save.
     * @throws ValidationException       In case of validation failure before save.
     * @throws RuntimeException          In case of any other exception during save.
     * @since 0.1.0
     */
    public EventTypeDto save(final CreateEventTypeRequestDto eventType) throws RuntimeException {
        validate(eventType);

        return null;
    }
}
