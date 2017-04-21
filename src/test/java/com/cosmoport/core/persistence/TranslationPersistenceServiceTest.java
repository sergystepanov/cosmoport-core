package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.TranslationDto;
import com.google.common.eventbus.EventBus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

final class TranslationPersistenceServiceTest extends PersistenceTest {
    private TranslationPersistenceService translationPersistenceService;

    @BeforeEach
    void setService() {
        super.before();

        translationPersistenceService = new TranslationPersistenceService(
                getDataSourceProvider(), new I18nPersistenceService(getLogger(), getDataSourceProvider()),
                new LocalePersistenceService(getLogger(), getDataSourceProvider()), new EventBus());
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
        Assertions.assertTrue(translationPersistenceService.getAll().size() > 2);
    }

    @Test
    @DisplayName("Should be able to update translation values")
    void updateTranslationForId() {
        final String text = "abc";

        translationPersistenceService.updateTranslationForId(1, text);
        final Optional<TranslationDto> result = translationPersistenceService.getById(1);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(text, result.get().getText());
    }
}