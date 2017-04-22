package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.EventTypeDto;
import com.cosmoport.core.dto.request.CreateEventTypeRequestDto;
import com.cosmoport.core.persistence.exception.ValidationException;
import com.google.common.eventbus.EventBus;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

final class EventTypePersistenceServiceTest extends PersistenceTest {
    private EventTypePersistenceService service;
    private TranslationPersistenceService translationService;
    private I18nPersistenceService i18nService;

    @BeforeEach
    void createPersistenceService() {
        super.before();

        i18nService = new I18nPersistenceService(getLogger(), getDataSourceProvider());
        translationService = new TranslationPersistenceService(
                getDataSourceProvider(),
                i18nService,
                new LocalePersistenceService(getLogger(), getDataSourceProvider()),
                new EventBus()
        );
        service = new EventTypePersistenceService(getLogger(), getDataSourceProvider(),
                i18nService,
                translationService);
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

    @Nested
    @DisplayName("Should be able to save/get/delete objects")
    class PersistenceTest {
        final int newCount = 5;
        final int localesCount = 3;

        @Test
        @DisplayName("Should save new event type")
        void save() {
            final EventTypeDto eventType = service.save(
                    new CreateEventTypeRequestDto("event_name", "event_subname", "event_description", 0, 0));
            assertAll("checks",
                    // created
                    () -> assertEquals(newCount, eventType.getId()),
                    // has i18ns
                    () -> assertTrue(i18nService.findByTag("event_type_name_" + newCount).isPresent()),
                    () -> assertTrue(i18nService.findByTag("event_type_subname_" + newCount).isPresent()),
                    () -> assertTrue(i18nService.findByTag("event_type_description_" + newCount).isPresent()),
                    // has default translations
                    () -> assertTrue(
                            translationService.findAllByI18n(eventType.getI18nEventTypeName()).size() == localesCount),
                    () -> assertTrue(
                            translationService.findAllByI18n(eventType.getI18nEventTypeDescription()).size() == localesCount),
                    () -> assertTrue(
                            translationService.findAllByI18n(eventType.getI18nEventTypeSubname()).size() == localesCount)
            );
        }
    }
}
