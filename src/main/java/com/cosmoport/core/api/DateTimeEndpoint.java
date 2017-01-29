package com.cosmoport.core.api;

import com.cosmoport.core.dto.DateTimeDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;

/**
 * Server time and date endpoint.
 *
 * @since 0.0.1
 */
@Path("/time")
@Produces(MediaType.APPLICATION_JSON)
public class DateTimeEndpoint {
    @GET
    @Path("/")
    public DateTimeDto getDateTime() {
        return new DateTimeDto(Instant.now().getEpochSecond());
    }
}
