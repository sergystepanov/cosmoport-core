package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventTypeDto;
import com.cosmoport.core.dto.I18nDto;
import com.cosmoport.core.dto.TranslationDto;
import com.cosmoport.core.dto.request.CreateEventTypeRequestDto;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import com.cosmoport.core.persistence.exception.ValidationException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Event type entity database service.
 *
 * @since 0.1.0
 */
public final class EventTypePersistenceService extends PersistenceService<EventTypeDto> {
    private final I18nPersistenceService i18nPersistenceService;
    private final TranslationPersistenceService translationPersistenceService;

    @Inject
    EventTypePersistenceService(Logger logger, Provider<DataSource> ds,
                                I18nPersistenceService i18nPersistenceService,
                                TranslationPersistenceService translationPersistenceService) {
        super(logger, ds);
        this.i18nPersistenceService = i18nPersistenceService;
        this.translationPersistenceService = translationPersistenceService;
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

        EventTypeDto newEventType = null;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getConnection();
            // Set manual commit on the connection and pass down it into other services
            conn.setAutoCommit(false);

            // Get max id for i18n tag name auto generation
            long maxId = this.getMaxId() + 1;

            // Actually, it's possible with only one SQL query
            // Make new i18n records
            final I18nDto eventTypeI18nName = i18nPersistenceService.save(
                    new I18nDto(0, "event_type_name_" + maxId, false, "", ""), conn);
            final I18nDto eventTypeI18nSubname = i18nPersistenceService.save(
                    new I18nDto(0, "event_type_subname_" + maxId, false, "", ""), conn);
            final I18nDto eventTypeI18nDescription = i18nPersistenceService.save(
                    new I18nDto(0, "event_type_description_" + maxId, false, "", ""), conn);

            // Link it with an event type record
            newEventType = new EventTypeDto(0,
                    eventTypeI18nName.getId(), eventTypeI18nSubname.getId(),
                    eventTypeI18nDescription.getId(),
                    eventType.getDefaultDuration(), eventType.getDefaultRepeatInterval()
            );
            statement = conn.prepareStatement(
                    "INSERT INTO EVENT_TYPE (i18n_event_type_name, i18n_event_type_subname," +
                            "i18n_event_type_description, default_duration, default_repeat_interval) " +
                            " VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, newEventType.getI18nEventTypeName());
            statement.setLong(2, newEventType.getI18nEventTypeSubname());
            statement.setLong(3, newEventType.getI18nEventTypeDescription());
            statement.setLong(4, newEventType.getDefaultDuration());
            statement.setLong(5, newEventType.getDefaultRepeatInterval());

            if (statement.executeUpdate() < 0) {
                throw new Exception();
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newEventType.setId(generatedKeys.getLong(1));
                } else {
                    throw new Exception();
                }
            }

            // Make default translation and copy it all over the rest ones
            // Default
            final TranslationDto trName = translationPersistenceService.save(
                    new TranslationDto(0, newEventType.getI18nEventTypeName(), 1, eventType.getName(), null), conn);
            final TranslationDto trSubname = translationPersistenceService.save(
                    new TranslationDto(0, newEventType.getI18nEventTypeSubname(), 1, eventType.getSubname(), null), conn);
            final TranslationDto trDescription = translationPersistenceService.save(
                    new TranslationDto(0, newEventType.getI18nEventTypeDescription(), 1, eventType.getDescription(), null), conn);

            // Copy
            translationPersistenceService.copyOf(trName, conn);
            translationPersistenceService.copyOf(trSubname, conn);
            translationPersistenceService.copyOf(trDescription, conn);

            conn.commit();
        } catch (SQLException sqlexception) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwConstrainViolation(sqlexception);
            throwServerApiException(sqlexception);
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            throwServerApiException(e);
        } finally {
            close(statement, conn);
        }

        return newEventType;
    }

    private long getMaxId() {
        long result = 0;

        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            conn = getConnection();

            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT MAX(id) FROM EVENT_TYPE");

            if (rs.next()) {
                result = rs.getLong(1);
            }
        } catch (Exception e) {
            throwServerApiException(e);
        } finally {
            close(rs, statement, conn);
        }

        return result;
    }
}
