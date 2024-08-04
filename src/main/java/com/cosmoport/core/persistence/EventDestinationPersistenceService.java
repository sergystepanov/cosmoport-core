package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventDestinationDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Event destination entity database service.
 *
 * @since 0.1.0
 */
@Service
public final class EventDestinationPersistenceService extends PersistenceService<EventDestinationDto> {
    EventDestinationPersistenceService(Logger logger, DataSource ds) {
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
