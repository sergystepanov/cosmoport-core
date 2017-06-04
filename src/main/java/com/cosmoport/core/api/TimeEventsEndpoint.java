package com.cosmoport.core.api;

import com.cosmoport.core.dto.EventDestinationDto;
import com.cosmoport.core.dto.EventReferenceDataDto;
import com.cosmoport.core.dto.EventStatusDto;
import com.cosmoport.core.dto.EventTypeDto;
import com.cosmoport.core.dto.request.CreateEventTypeRequestDto;
import com.cosmoport.core.persistence.EventDestinationPersistenceService;
import com.cosmoport.core.persistence.EventStatusPersistenceService;
import com.cosmoport.core.persistence.EventTypePersistenceService;
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
    private final EventDestinationPersistenceService eventDestinationPersistenceService;

    @Inject
    public TimeEventsEndpoint(EventTypePersistenceService eventTypePersistenceService,
                              EventStatusPersistenceService eventStatusPersistenceService,
                              EventDestinationPersistenceService eventDestinationPersistenceService) {
        this.eventTypePersistenceService = eventTypePersistenceService;
        this.eventStatusPersistenceService = eventStatusPersistenceService;
        this.eventDestinationPersistenceService = eventDestinationPersistenceService;
    }

    @GET
    @Path("/reference_data")
    public EventReferenceDataDto getEventReferenceData() {
        return new EventReferenceDataDto(
                eventTypePersistenceService.getAll(),
                eventStatusPersistenceService.getAll(),
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
        return "{\"deleted\": " + eventTypePersistenceService.delete(id) + '}';
    }

    @GET
    @Path("/statuses")
    public List<EventStatusDto> getEventStatuses() {
        return eventStatusPersistenceService.getAll();
    }

    @GET
    @Path("/destinations")
    public List<EventDestinationDto> getEventDestinations() {
        return eventDestinationPersistenceService.getAll();
    }
}
