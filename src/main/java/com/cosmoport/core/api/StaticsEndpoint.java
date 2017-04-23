package com.cosmoport.core.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Static files endpoint of the application.
 *
 * @since 1.0.0
 */
@Path("/")
public class StaticsEndpoint {
    @GET
    @Path("/favicon.ico")
    public Response getFavicon() {
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
