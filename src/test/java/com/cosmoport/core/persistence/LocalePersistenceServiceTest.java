package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.LocaleDto;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ClearDatabase
final class LocalePersistenceServiceTest {

    @Autowired
    private LocalePersistenceService service;

    @Test
    @DisplayName("Should be able to execute getAll()")
    void getAll() {
        assertTrue(service.getAll().size() > 2);
    }

    @Test
    @DisplayName("Should be able to create new locale")
    void createLocale() throws UniqueConstraintException {
        final LocaleDto newLocale = new LocaleDto(0, "de", false, "Deu", false, 1);

        assertEquals(newLocale.getCode(), service.createLocale(newLocale).getCode());
    }

    @Test
    @DisplayName("Should be able to update show data of locales")
    void updateLocaleShowData() {
        final LocaleDto locale = new LocaleDto(1, "", false, "", false, 20);
        service.updateLocaleShowData(locale);
        final LocaleDto persistent = service.findById(1).orElse(new LocaleDto(2, "", false, "", true, 10));

        assertAll("data",
                () -> assertFalse(persistent.isShow()),
                () -> assertEquals(20, persistent.getShowTime())
        );
    }
}
