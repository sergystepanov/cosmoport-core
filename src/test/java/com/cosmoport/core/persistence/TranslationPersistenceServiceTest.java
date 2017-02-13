package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.TranslationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

final class TranslationPersistenceServiceTest extends PersistenceTest {
    private TranslationPersistenceService translationPersistenceService;

    @BeforeEach
    void setService() {
        translationPersistenceService = new TranslationPersistenceService(
                getDataSourceProvider(), new I18nPersistenceService(getLogger(), getDataSourceProvider()));
    }

    @Test
    @DisplayName("Should be able to execute getAllByLocaleId()")
    void getAllByLocaleId() {
        final TranslationDto translation = translationPersistenceService.getAllByLocaleId(1).get(0);

        Assertions.assertEquals(1, translation.getI18n().getId());
    }

    @Test
    @DisplayName("Should be able to execute getAll()")
    void getAll() {
        Assertions.assertEquals(3, translationPersistenceService.getAll().size());
    }
}