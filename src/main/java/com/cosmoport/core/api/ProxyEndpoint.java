package com.cosmoport.core.api;

import com.cosmoport.core.dto.ProxyRequestDto;
import com.cosmoport.core.event.message.FireUpGateMessage;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/proxy")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public final class ProxyEndpoint {
    private final EventBus eventBus;

    @Inject
    public ProxyEndpoint(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @POST
    @Path("/")
    public String reactOn(final ProxyRequestDto request) {
        if (request.getName().equals("fire_gate")) {
            eventBus.post(new FireUpGateMessage(request.getEvent(), request.getType()));
        }

        return "Success";
    }
}
