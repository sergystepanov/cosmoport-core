package com.cosmoport.core.api;

import com.cosmoport.core.dto.EventDto;
import com.cosmoport.core.event.message.ReloadMessage;
import com.cosmoport.core.event.message.SyncTimetablesMessage;
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
    private final TimetablePersistenceService service;
    private final EventBus eventBus;

    @Inject
    public TimetableEndpoint(TimetablePersistenceService service, EventBus eventBus) {
        this.service = service;
        this.eventBus = eventBus;
    }

    @GET
    @Path("/")
    public List<EventDto> get(@QueryParam("date") String date, @QueryParam("gate") long gateId) {
        return service.getAllWithFilter(date, gateId);
    }

    @GET
    @Path("/all")
    public List<EventDto> getAll(@QueryParam("page") int page, @QueryParam("count") int count) {
        final List<EventDto> events = service.getAllPage(page, count);
        // We assume that this method will be called on every timetable app opening
        eventBus.post(new SyncTimetablesMessage());

        return events;
    }

    /**
     * Gets the event with {@code id} and one event after that for same gate.
     *
     * @param id long An id of event.
     * @return Two events.
     */
    @GET
    @Path("/byIdAndOneAfter")
    public List<EventDto> getEvents(@QueryParam("id") long id) {
        return service.getCustomByIdForGate(id);
    }

    @POST
    @Path("/")
    public EventDto create(final EventDto event) {
        final EventDto newEvent = service.save(event);
        eventBus.post(new ReloadMessage());

        return newEvent;
    }

    @POST
    @Path("/update/event")
    public EventDto update(final EventDto event) {
        final EventDto newEvent = service.save(event);
        eventBus.post(new ReloadMessage());

        return newEvent;
    }

    @DELETE
    @Path("/{id}")
    public String delete(@PathParam("id") final long id) {
        final String deleted = "{\"deleted\": " + service.delete(id) + '}';
        eventBus.post(new ReloadMessage());

        return deleted;
    }
}
