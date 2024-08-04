package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.request.CreateEventTypeCategoryRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ClearDatabase
class EventTypeCategoryPersistenceServiceTest {

    @Autowired
    private TranslationPersistenceService translationPersistenceService;

    @Autowired
    private EventTypeCategoryPersistenceService service;

    @Test
    @DisplayName("Should be able to execute getAll()")
    void getAll() {
        var result = service.getAll();

        assertAll(
                () -> assertFalse(result.isEmpty()),
                () -> {
                    for (var i : result) {
                        assertTrue(i.getI18nEventTypeCategoryName() > 0);
                    }
                }
        );
    }

    @Test
    @DisplayName("Should be able to create new event type categories")
    void create() {
        final var rq = new CreateEventTypeCategoryRequestDto("test");

        final var result = service.create(rq);
        final var trs = translationPersistenceService.findAllByI18n(result.getI18nEventTypeCategoryName());

        assertAll(
                () -> assertTrue(result.getId() > 0),
                () -> assertEquals(rq.name(), trs.getFirst().getText()),
                // same names are not allowed
                () -> assertThrows(RuntimeException.class, () -> service.create(rq))
        );
    }
}
