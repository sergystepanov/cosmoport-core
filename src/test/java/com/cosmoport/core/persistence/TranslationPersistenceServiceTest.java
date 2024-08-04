package com.cosmoport.core.persistence;

import com.cosmoport.core.dto.TranslationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@ClearDatabase
final class TranslationPersistenceServiceTest {

    @Autowired
    private TranslationPersistenceService translationPersistenceService;

    @Test
    @DisplayName("Should be able to execute getAllByLocaleId()")
    void getAllByLocaleId() {
        final TranslationDto translation = translationPersistenceService.getAllByLocaleId(1).getFirst();

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
