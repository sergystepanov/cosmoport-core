package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.request.CreateEventTypeRequestDto;
import com.cosmoport.core.persistence.exception.ValidationException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class EventTypePersistenceServiceTest extends PersistenceTest {
    private EventTypePersistenceService service;

    @BeforeEach
    void createPersistenceService() {
        super.before();

        service = new EventTypePersistenceService(getLogger(), getDataSourceProvider());
    }

    @Test
    @DisplayName("Should be able to execute getAll()")
    void getAll() {
        Assertions.assertEquals(4, service.getAll().size());
    }

    @Nested
    @DisplayName("Should be able to validate input values")
    class ValidationTest {
        @Test
        @DisplayName("Validate duplicates by name")
        void validateStart() {
            final Throwable exception = assertThrows(
                    ValidationException.class,
                    () -> service.save(new CreateEventTypeRequestDto("Excursion", "Station lunch", "", 0, 0))
            );

            assertEquals("Duplicate with Excursion, Station lunch", exception.getMessage());
        }
    }
}
