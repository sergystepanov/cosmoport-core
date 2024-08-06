package com.cosmoport.core.api;

import com.cosmoport.core.dto.LocaleDto;
import com.cosmoport.core.dto.ResultDto;
import com.cosmoport.core.dto.TranslationDto;
import com.cosmoport.core.dto.TranslationLightDto;
import com.cosmoport.core.dto.request.TextValueUpdateRequestDto;
import com.cosmoport.core.event.ReloadMessage;
import com.cosmoport.core.event.TimeoutUpdateMessage;
import com.cosmoport.core.persistence.LocalePersistenceService;
import com.cosmoport.core.persistence.TranslationPersistenceService;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/translations")
public class TranslationEndpoint {
    private final TranslationPersistenceService translationPersistenceService;
    private final LocalePersistenceService localePersistenceService;
    private final ApplicationEventPublisher bus;

    public TranslationEndpoint(TranslationPersistenceService translationPersistenceService,
                               LocalePersistenceService localePersistenceService,
                               ApplicationEventPublisher bus) {
        this.translationPersistenceService = translationPersistenceService;
        this.localePersistenceService = localePersistenceService;
        this.bus = bus;
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
            bus.publishEvent(new ReloadMessage(this));
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
        bus.publishEvent(new TimeoutUpdateMessage(this));
        return newLocale;
    }
}
