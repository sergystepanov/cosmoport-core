package com.cosmoport.core.statics;

import com.google.inject.Singleton;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Static files endpoint of the application.
 *
 * @since 1.0.0
 */
@Path("/")
@Singleton
public class StaticsEndpoint {
    @GET
    @Path("/favicon.ico")
    @Produces("image/x-icon")
    public byte[] getFavicon() {
        // just an empty icon
        return new byte[]{
                0, 0, 1, 0, 1, 0, 16, 16, 2, 0, 1, 0, 1, 0, (byte) 176, 0, 0, 0, 22, 0, 0, 0, 40, 0, 0, 0, 16, 0, 0, 0,
                32, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, (byte) 128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, (byte) 255, (byte) 255, (byte) 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, (byte) 255, (byte) 255, 0, 0, (byte) 255, (byte) 255, 0, 0,
                (byte) 255, (byte) 255, 0, 0, (byte) 255, (byte) 255, 0, 0, (byte) 255, (byte) 255, 0, 0, (byte) 255,
                (byte) 255, 0, 0, (byte) 255, (byte) 255, 0, 0, (byte) 255, (byte) 255, 0, 0, (byte) 255, (byte) 255,
                0, 0, (byte) 255, (byte) 255, 0, 0, (byte) 255, (byte) 255, 0, 0, (byte) 255, (byte) 255, 0, 0,
                (byte) 255, (byte) 255, 0, 0, (byte) 255, (byte) 255, 0, 0, (byte) 255, (byte) 255, 0, 0, (byte) 255,
                (byte) 255, 0, 0};
    }
}
