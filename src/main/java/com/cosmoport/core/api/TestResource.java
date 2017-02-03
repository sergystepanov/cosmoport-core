package com.cosmoport.core.api;

import com.cosmoport.core.event.message.TestMessage;
import com.cosmoport.core.persistence.TestPersistenceService;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jboss.resteasy.annotations.GZIP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/test")
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public class TestResource {
    private static Logger logger = LoggerFactory.getLogger(TestResource.class.getCanonicalName());
    private final TestPersistenceService testPersistenceService;
    private EventBus eventBus;

    @Inject
    public TestResource(TestPersistenceService userPersistenceService, EventBus eventBus) {
        this.testPersistenceService = userPersistenceService;
        this.eventBus = eventBus;
    }

    @GET
    @Path("/")
    public Response get() {
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(testPersistenceService.getAll())
                .build();
    }

    @POST
    @Path("/event")
    public String post() throws Exception {
        final long id = testPersistenceService.save();
        eventBus.post(new TestMessage());
        return "{\"message\": \"ok\", \"id\": " + id + "}";

    }

    @GET
    @Path("/delete")
    public String delete(@QueryParam("id") int id) {
        testPersistenceService.remove(id);
        eventBus.post(new TestMessage());

        return "{\"r\":\"ok\"}";
    }

    @GET
    @Path("/event")
    public String event() {
        eventBus.post(new TestMessage());


        return "an event";
    }
}
