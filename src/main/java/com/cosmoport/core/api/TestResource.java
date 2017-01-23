package com.cosmoport.core.api;

import com.cosmoport.core.dto.TestDto;
import com.cosmoport.core.persistence.TestPersistenceService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/test")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public class TestResource {
    private final TestPersistenceService testPersistenceService;

    @Inject
    public TestResource(TestPersistenceService userPersistenceService) {
        this.testPersistenceService = userPersistenceService;
    }

    @GET
    @Path("/")
    public List<TestDto> get() {
        return testPersistenceService.getAll();
    }
}
