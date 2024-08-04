package com.cosmoport.core.api;

import com.cosmoport.core.dto.LocaleDto;
import com.cosmoport.core.dto.ResultDto;
import com.cosmoport.core.dto.TranslationDto;
import com.cosmoport.core.dto.TranslationLightDto;
import com.cosmoport.core.dto.request.TextValueUpdateRequestDto;
import com.cosmoport.core.event.message.ReloadMessage;
import com.cosmoport.core.event.message.TimeoutUpdateMessage;
import com.cosmoport.core.persistence.LocalePersistenceService;
import com.cosmoport.core.persistence.TranslationPersistenceService;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/translations")
public class TranslationEndpoint {
    private final TranslationPersistenceService translationPersistenceService;
    private final LocalePersistenceService localePersistenceService;
    private final EventBus eventBus;
    private final Logger logger;

    public TranslationEndpoint(TranslationPersistenceService translationPersistenceService,
                               LocalePersistenceService localePersistenceService,
                               EventBus eventBus, Logger logger) {
        this.translationPersistenceService = translationPersistenceService;
        this.localePersistenceService = localePersistenceService;
        this.eventBus = eventBus;
        this.logger = logger;
    }

    @GetMapping
    public Map<String, Map<String, TranslationLightDto>> get() {
        return translationPersistenceService.getAll();
    }

    @GetMapping("/localeId={localeId}")
    public List<TranslationDto> getTranslations(@PathVariable("localeId") long localeId) {
        return translationPersistenceService.getAllByLocaleId(localeId);
    }

    @PostMapping("/update/{translationId}")
    public ResultDto updateTranslation(@PathVariable("translationId") long translationId,
                                       @RequestBody TextValueUpdateRequestDto requestDto) {
        final boolean updated =
                translationPersistenceService.updateTranslationForId(translationId, requestDto.text());
        if (updated) {
            eventBus.post(new ReloadMessage());
        }

        return new ResultDto(updated);
    }

    @GetMapping("/locales")
    public List<LocaleDto> getLocales() {
        return localePersistenceService.getAll();
    }

    @GetMapping("/locales/visible")
    public List<LocaleDto> getVisibleLocales() {
        return localePersistenceService.getAllVisible();
    }

    @PostMapping("/locale")
    public LocaleDto createLocale(@RequestBody LocaleDto locale) throws UniqueConstraintException {
        return localePersistenceService.createLocale(locale);
    }

    @PostMapping("/locale/show")
    public LocaleDto setLocaleShowData(@RequestBody LocaleDto locale) {
        final LocaleDto newLocale = localePersistenceService.updateLocaleShowData(locale);
        eventBus.post(new TimeoutUpdateMessage());

        logger.info("{}", eventBus.identifier());

        return newLocale;
    }
}
