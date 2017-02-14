package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.LocaleDto;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class LocalePersistenceServiceTest extends PersistenceTest {
    private LocalePersistenceService service;

    @BeforeEach
    void setService() {
        service = new LocalePersistenceService(getLogger(), getDataSourceProvider());
    }

    @Test
    @DisplayName("Should be able to execute getAll()")
    void getAll() {
        Assertions.assertEquals(3, service.getAll().size());
    }

    @Test
    @DisplayName("Should be able to create new locale")
    void createLocale() throws UniqueConstraintException {
        final LocaleDto newLocale = new LocaleDto(0, "de", false, "Deu");

        final LocaleDto created = service.createLocale(newLocale);

        Assertions.assertEquals(newLocale.getCode(), created.getCode());
    }
}
