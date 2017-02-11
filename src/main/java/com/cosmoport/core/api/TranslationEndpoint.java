package com.cosmoport.core.api;

import com.cosmoport.core.dto.TranslationDto;
import com.cosmoport.core.persistence.TranslationPersistenceService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/translations")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public class TranslationEndpoint {
    private final TranslationPersistenceService translationPersistenceService;

    @Inject
    public TranslationEndpoint(TranslationPersistenceService translationPersistenceService) {
        this.translationPersistenceService = translationPersistenceService;
    }

    @GET
    @Path("/")
    public Map<String, Map<String, TranslationDto>> get() {
        return translationPersistenceService.getAll();
    }
}
