package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventDto;
import com.cosmoport.core.persistence.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

final class TimetablePersistenceServiceTest extends PersistenceTest {
    @Test
    @DisplayName("Should be able to execute getAll()")
    void getAll() throws Exception {
        final TimetablePersistenceService service =
                new TimetablePersistenceService(getLogger(), getDataSourceProvider());

        Assertions.assertEquals(10, service.getAll().size());
    }

    @Test
    @DisplayName("Should be able execute getAllWithFilter()")
    void getAllWithFilter() {
        final TimetablePersistenceService service =
                new TimetablePersistenceService(getLogger(), getDataSourceProvider());

        Assertions.assertEquals(10, service.getAllWithFilter(null, null).size());
        Assertions.assertEquals(10, service.getAllWithFilter("2017-02-05", null).size());
        Assertions.assertEquals(10, service.getAllWithFilter(null, 1L).size());
        Assertions.assertEquals(0, service.getAllWithFilter("2015-01-01", 1L).size());
    }

    @Test
    @DisplayName("Should be able to validate overlapping by start time")
    void validateStart() {
        final TimetablePersistenceService service =
                new TimetablePersistenceService(getLogger(), getDataSourceProvider());

        final Throwable exception = assertThrows(
                ValidationException.class,
                () -> service.save(new EventDto(0, "2017-02-05", 2, 6, 1, 1, 540, 30, 20, 10, 1, 0, ""))
        );

        Assertions.assertEquals("Overlapping events: [1] start: 540, end: 570", exception.getMessage());
    }

    @Test
    @DisplayName("Should be able to validate overlapping by end time")
    void validateEnd() {
        final TimetablePersistenceService service =
                new TimetablePersistenceService(getLogger(), getDataSourceProvider());

        final Throwable exception = assertThrows(
                ValidationException.class,
                () -> service.save(new EventDto(0, "2017-02-05", 2, 6, 1, 1, 500, 70, 20, 10, 1, 0, ""))
        );

        Assertions.assertEquals("Overlapping events: [1] start: 540, end: 570", exception.getMessage());
    }
}