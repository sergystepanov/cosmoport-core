package com.cosmoport.core.api;

import com.cosmoport.core.dto.ResultDto;
import com.cosmoport.core.dto.SettingsDto;
import com.cosmoport.core.dto.request.TextValueUpdateRequestDto;
import com.cosmoport.core.event.message.ReloadMessage;
import com.cosmoport.core.persistence.SettingsPersistenceService;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/settings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public final class SettingsEndpoint {
    private final SettingsPersistenceService service;
    private final EventBus eventBus;

    @Inject
    public SettingsEndpoint(SettingsPersistenceService settingsPersistenceService, EventBus eventBus) {
        this.service = settingsPersistenceService;
        this.eventBus = eventBus;
    }

    @GET
    @Path("/")
    public List<SettingsDto> getSettings() {
        return service.getAllWithoutProtectedValues();
    }

    @POST
    @Path("/update/{id}")
    public ResultDto updateSetting(@PathParam("id") long id, final TextValueUpdateRequestDto requestDto) {
        final boolean updated = service.updateSettingForId(id, requestDto.getText());
        if (updated) {
            eventBus.post(new ReloadMessage());
        }

        return new ResultDto(updated);
    }
}
