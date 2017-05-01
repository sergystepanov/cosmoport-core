package com.cosmoport.core.api;

import com.cosmoport.core.dto.LocaleDto;
import com.cosmoport.core.dto.ResultDto;
import com.cosmoport.core.dto.TranslationDto;
import com.cosmoport.core.dto.TranslationLightDto;
import com.cosmoport.core.dto.request.TranslationTextUpdateRequestDto;
import com.cosmoport.core.event.message.ReloadMessage;
import com.cosmoport.core.event.message.TimeoutUpdateMessage;
import com.cosmoport.core.persistence.LocalePersistenceService;
import com.cosmoport.core.persistence.TranslationPersistenceService;
import com.cosmoport.core.persistence.exception.UniqueConstraintException;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/translations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public class TranslationEndpoint {
    private final TranslationPersistenceService translationPersistenceService;
    private final LocalePersistenceService localePersistenceService;
    private final EventBus eventBus;

    @Inject
    public TranslationEndpoint(TranslationPersistenceService translationPersistenceService,
                               LocalePersistenceService localePersistenceService,
                               EventBus eventBus) {
        this.translationPersistenceService = translationPersistenceService;
        this.localePersistenceService = localePersistenceService;
        this.eventBus = eventBus;
    }

    @GET
    @Path("/")
    public Map<String, Map<String, TranslationLightDto>> get() {
        return translationPersistenceService.getAll();
    }

    @GET
    @Path("/localeId={localeId}")
    public List<TranslationDto> getTranslations(@PathParam("localeId") long localeId) {
        return translationPersistenceService.getAllByLocaleId(localeId);
    }

    @POST
    @Path("/update/{translationId}")
    public ResultDto updateTranslation(@PathParam("translationId") long translationId,
                                       TranslationTextUpdateRequestDto requestDto) {
        final boolean updated =
                translationPersistenceService.updateTranslationForId(translationId, requestDto.getText());
        if (updated) {
            eventBus.post(new ReloadMessage());
        }

        return new ResultDto(updated);
    }

    @GET
    @Path("/locales")
    public List<LocaleDto> getLocales() {
        return localePersistenceService.getAll();
    }

    @GET
    @Path("/locales/visible")
    public List<LocaleDto> getVisibleLocales() {
        return localePersistenceService.getAllVisible();
    }

    @POST
    @Path("/locale")
    public LocaleDto createLocale(final LocaleDto locale) throws UniqueConstraintException {
        return localePersistenceService.createLocale(locale);
    }

    @POST
    @Path("/locale/show")
    public LocaleDto setLocaleShowData(final LocaleDto locale) {
        final LocaleDto newLocale = localePersistenceService.updateLocaleShowData(locale);
        eventBus.post(new TimeoutUpdateMessage());

        return newLocale;
    }
}
