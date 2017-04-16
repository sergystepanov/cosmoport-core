package com.cosmoport.core.api;

import com.cosmoport.core.dto.EventDto;
import com.cosmoport.core.event.message.ReloadMessage;
import com.cosmoport.core.persistence.TimetablePersistenceService;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/timetable")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public final class TimetableEndpoint {
    private final TimetablePersistenceService timetablePersistenceService;
    private final EventBus eventBus;

    @Inject
    public TimetableEndpoint(TimetablePersistenceService timetablePersistenceService, EventBus eventBus) {
        this.timetablePersistenceService = timetablePersistenceService;
        this.eventBus = eventBus;
    }

    @GET
    @Path("/")
    public List<EventDto> get(@QueryParam("date") String date, @QueryParam("gate") long gateId) {
        return timetablePersistenceService.getAllWithFilter(date, gateId);
    }

    @POST
    @Path("/")
    public EventDto create(final EventDto event) {
        final EventDto newEvent = timetablePersistenceService.save(event);
        eventBus.post(new ReloadMessage());

        return newEvent;
    }

    @DELETE
    @Path("/{id}")
    public String delete(@PathParam("id") final long id) {
        return "{\"deleted\": " + timetablePersistenceService.delete(id) + '}';
    }
}
