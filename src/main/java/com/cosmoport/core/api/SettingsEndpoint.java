package com.cosmoport.core.api;

import com.cosmoport.core.dto.SettingsDto;
import com.cosmoport.core.persistence.SettingsPersistenceService;
import com.google.inject.Inject;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/settings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public final class SettingsEndpoint {
    private final SettingsPersistenceService settingsPersistenceService;

    @Inject
    public SettingsEndpoint(SettingsPersistenceService settingsPersistenceService) {
        this.settingsPersistenceService = settingsPersistenceService;
    }

    @GET
    @Path("/")
    public List<SettingsDto> getSettings() {
        return settingsPersistenceService.getAll();
    }
}
