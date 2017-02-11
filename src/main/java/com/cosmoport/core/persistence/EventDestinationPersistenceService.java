package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventDestinationDto;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Event destination entity database service.
 *
 * @since 0.1.0
 */
public final class EventDestinationPersistenceService extends PersistenceService<EventDestinationDto> {
    @Inject
    EventDestinationPersistenceService(Logger logger, Provider<DataSource> ds) {
        super(logger, ds);
    }

    public List<EventDestinationDto> getAll() {
        return getAll("SELECT * FROM EVENT_DESTINATION");
    }

    @Override
    protected EventDestinationDto map(final ResultSet rs) throws SQLException {
        return new EventDestinationDto(rs.getLong("id"), rs.getLong("i18n_event_destination_name"));
    }
}
