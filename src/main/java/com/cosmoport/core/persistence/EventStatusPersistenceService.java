package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventStatusDto;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Event status entity database service.
 *
 * @since 0.1.0
 */
@Service
public final class EventStatusPersistenceService extends PersistenceService<EventStatusDto> {
    EventStatusPersistenceService(Logger logger, DataSource ds) {
        super(logger, ds);
    }

    @Override
    protected EventStatusDto map(final ResultSet rs) throws SQLException {
        return new EventStatusDto(rs.getLong("id"), rs.getLong("i18n_status"));
    }

    public List<EventStatusDto> getAll() {
        return getAll("SELECT * FROM EVENT_STATUS");
    }
}
