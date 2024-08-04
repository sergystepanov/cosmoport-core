package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventStateDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Event state entity database service.
 *
 * @since 0.1.2
 */
@Service
public final class EventStatePersistenceService extends PersistenceService<EventStateDto> {
    EventStatePersistenceService(Logger logger, DataSource ds) {
        super(logger, ds);
    }

    @Override
    protected EventStateDto map(final ResultSet rs) throws SQLException {
        return new EventStateDto(rs.getLong("id"), rs.getLong("i18n_state"));
    }

    public List<EventStateDto> getAll() {
        return getAll("SELECT * FROM EVENT_STATE");
    }
}
