package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventTypeDto;
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
}

