package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventDto;
import com.cosmoport.core.persistence.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

final class TimetablePersistenceServiceTest extends PersistenceTest {
    private TimetablePersistenceService service;

    @BeforeEach
    void createPersistenceService() {
        super.before();

        service = new TimetablePersistenceService(getLogger(), getDataSourceProvider());
    }

    @Nested
    @DisplayName("Should be able to save/get/delete objects")
    class PersistenceTest {
        final int total = 10;

        @Test
        @DisplayName("Should be able to execute save()")
        void save() {
            assertEquals(
                    total + 1,
                    service.save(new EventDto(0, "2017-02-05", 2, 6, 1, 1, 1, 1, 1, 1, 20, 10, 1, 0, "")).getId()
            );
        }

        @Test
        @DisplayName("Should be able to execute getAll()")
        void getAll() {
            assertEquals(total, service.getAll().size());
        }

        @Test
        @DisplayName("Should be able to execute getAll() with paging")
        void getAllPage() {
            assertEquals(5, service.getAllPage(1, 5).size());
        }

        @Test
        @DisplayName("Should be able execute getAllWithFilter()")
        void getAllWithFilter() {
            assertAll("events",
                    () -> assertEquals(total, service.getAllWithFilter(null, null).size()),
                    () -> assertEquals(total, service.getAllWithFilter("2017-02-05", null).size()),
                    () -> assertEquals(total, service.getAllWithFilter(null, 1L).size()),
                    () -> assertEquals(0, service.getAllWithFilter("2015-01-01", 1L).size())
            );
        }

        @Test
        @DisplayName("Should be able to execute delete()")
        void delete() {
            assertTrue(service.delete(1));
        }
    }

    @Test
    @DisplayName("Should be able to select near events")
    void getCustomByIdForGate() {
        final List<EventDto> events = service.getCustomByIdForGate(5);

        assertAll("events",
                () -> assertEquals(5, events.get(0).getId()),
                () -> assertEquals(7, events.get(1).getId())
        );
    }

    @Nested
    @DisplayName("Should be able to validate input values")
    class ValidationTest {
        @Test
        @DisplayName("Validate overlapping by start time")
        void validateStart() {
            final Throwable exception = assertThrows(
                    ValidationException.class,
                    () -> service.save(new EventDto(0, "2017-02-05", 2, 6, 1, 1, 1, 1, 540, 30, 20, 10, 1, 0, ""))
            );

            assertEquals("Overlapping events: id: 1 [gate: 1→1] start: 540, end: 570", exception.getMessage());
        }

        @Test
        @DisplayName("Validate overlapping by end time")
        void validateEnd() {
            final Throwable exception = assertThrows(
                    ValidationException.class,
                    () -> service.save(new EventDto(0, "2017-02-05", 2, 6, 1, 1, 1, 1, 500, 70, 20, 10, 1, 0, ""))
            );

            assertEquals("Overlapping events: id: 1 [gate: 1→1] start: 540, end: 570", exception.getMessage());
        }

        @Test
        @DisplayName("Validate in-database people quota")
        void peopleLimit() {
            assertEquals("check_people_limit", assertThrows(
                    ValidationException.class,
                    () -> service.save(new EventDto(0, "2017-02-05", 2, 6, 1, 1, 1, 1, 500, 10, 20, 10, 1, 1000, ""))
            ).getMessage());
        }

        @Test
        @DisplayName("Validate in-database day minutes limit")
        void dayLimit() {
            assertEquals("check_minutes_in_day_limit", assertThrows(
                    ValidationException.class,
                    () -> service.save(new EventDto(0, "2017-02-05", 2, 6, 1, 1, 1, 1, 5000, 2210, 20, 10, 1, 0, ""))
            ).getMessage());
        }

        @Test
        @DisplayName("Validate in-database total duration day limit")
        void durationLimit() {
            assertEquals("check_event_duration_less_a_day", assertThrows(
                    ValidationException.class,
                    () -> service.save(new EventDto(0, "2017-02-05", 2, 6, 1, 1, 1, 1, 100, 1341, 20, 10, 1, 0, ""))
            ).getMessage());
        }
    }
}
