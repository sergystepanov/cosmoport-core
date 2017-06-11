package com.cosmoport.core.api;

import com.cosmoport.core.dto.*;
import com.cosmoport.core.dto.request.CreateEventTypeRequestDto;
import com.cosmoport.core.event.message.ReloadMessage;
import com.cosmoport.core.persistence.EventDestinationPersistenceService;
import com.cosmoport.core.persistence.EventStatePersistenceService;
import com.cosmoport.core.persistence.EventStatusPersistenceService;
import com.cosmoport.core.persistence.EventTypePersistenceService;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/t_events")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public final class TimeEventsEndpoint {
    private final EventTypePersistenceService eventTypePersistenceService;
    private final EventStatusPersistenceService eventStatusPersistenceService;
    private final EventStatePersistenceService eventStatePersistenceService;
    private final EventDestinationPersistenceService eventDestinationPersistenceService;
    private final EventBus eventBus;

    @Inject
    public TimeEventsEndpoint(EventTypePersistenceService eventTypePersistenceService,
                              EventStatusPersistenceService eventStatusPersistenceService,
                              EventStatePersistenceService eventStatePersistenceService,
                              EventDestinationPersistenceService eventDestinationPersistenceService,
                              EventBus eventBus) {
        this.eventTypePersistenceService = eventTypePersistenceService;
        this.eventStatusPersistenceService = eventStatusPersistenceService;
        this.eventStatePersistenceService = eventStatePersistenceService;
        this.eventDestinationPersistenceService = eventDestinationPersistenceService;
        this.eventBus = eventBus;
    }

    @GET
    @Path("/reference_data")
    public EventReferenceDataDto getEventReferenceData() {
        return new EventReferenceDataDto(
                eventTypePersistenceService.getAll(),
                eventStatusPersistenceService.getAll(),
                eventStatePersistenceService.getAll(),
                eventDestinationPersistenceService.getAll());
    }

    @GET
    @Path("/types")
    public List<EventTypeDto> getEventTypes() {
        return eventTypePersistenceService.getAll();
    }

    @POST
    @Path("/types")
    public EventTypeDto createEventType(final CreateEventTypeRequestDto eventType) {
        return eventTypePersistenceService.save(eventType);
    }

    @DELETE
    @Path("/types/{id}")
    public String delete(@PathParam("id") final long id) {
        final String result = "{\"deleted\": " + eventTypePersistenceService.delete(id) + '}';
        eventBus.post(new ReloadMessage());

        return result;
    }

    @GET
    @Path("/statuses")
    public List<EventStatusDto> getEventStatuses() {
        return eventStatusPersistenceService.getAll();
    }

    @GET
    @Path("/states")
    public List<EventStateDto> getEventStates() {
        return eventStatePersistenceService.getAll();
    }

    @GET
    @Path("/destinations")
    public List<EventDestinationDto> getEventDestinations() {
        return eventDestinationPersistenceService.getAll();
    }
}
