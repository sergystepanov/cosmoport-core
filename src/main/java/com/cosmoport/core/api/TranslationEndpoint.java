package com.cosmoport.core.api;

import com.cosmoport.core.dto.LocaleDto;
import com.cosmoport.core.dto.TranslationDto;
import com.cosmoport.core.dto.TranslationLightDto;
import com.cosmoport.core.persistence.LocalePersistenceService;
import com.cosmoport.core.persistence.TranslationPersistenceService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/translations")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public class TranslationEndpoint {
    private final TranslationPersistenceService translationPersistenceService;
    private final LocalePersistenceService localePersistenceService;

    @Inject
    public TranslationEndpoint(TranslationPersistenceService translationPersistenceService,
                               LocalePersistenceService localePersistenceService) {
        this.translationPersistenceService = translationPersistenceService;
        this.localePersistenceService = localePersistenceService;
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

    @GET
    @Path("/locales")
    public List<LocaleDto> getLocales() {
        return localePersistenceService.getAll();
    }
}
