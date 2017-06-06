package com.cosmoport.core.api;

import com.cosmoport.core.dto.GateDto;
import com.cosmoport.core.persistence.GatePersistenceService;
import com.google.inject.Inject;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/gates")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public class GateEndpoint {
    private final GatePersistenceService gatePersistenceService;

    @Inject
    public GateEndpoint(GatePersistenceService gatePersistenceService) {
        this.gatePersistenceService = gatePersistenceService;
    }

    @GET
    @Path("/")
    public List<GateDto> getAll() {
       return gatePersistenceService.getAll();
    }
}
