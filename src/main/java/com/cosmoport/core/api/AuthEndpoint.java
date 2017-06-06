package com.cosmoport.core.api;

import com.cosmoport.core.dto.PasswordDto;
import com.cosmoport.core.dto.ResultDto;
import com.cosmoport.core.persistence.SettingsPersistenceService;
import com.google.inject.Inject;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public final class AuthEndpoint {
    private final SettingsPersistenceService service;

    @Inject
    public AuthEndpoint(SettingsPersistenceService settingsPersistenceService) {
        this.service = settingsPersistenceService;
    }

    @POST
    @Path("/check")
    public ResultDto check(final PasswordDto password) {
        return new ResultDto(service.paramEquals("password", password.getPwd()));
    }

    @POST
    @Path("/set")
    public ResultDto set(final PasswordDto password) {
        return new ResultDto(service.updateSettingForParam("password", password.getPwd()));
    }

//    @POST
//    @Path("/update/{id}")
//    public ResultDto updateSetting(@PathParam("id") long id, final TextValueUpdateRequestDto requestDto) {
//        final boolean updated = service.updateSettingForId(id, requestDto.getText());
//        if (updated) {
//            eventBus.post(new ReloadMessage());
//        }
//
//        return new ResultDto(updated);
//    }
}
