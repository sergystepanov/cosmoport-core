package com.cosmoport.core.api;

import com.cosmoport.core.dto.*;
import com.cosmoport.core.dto.request.CreateEventTypeCategoryRequestDto;
import com.cosmoport.core.dto.request.CreateEventTypeRequestDto;
import com.cosmoport.core.event.message.ReloadMessage;
import com.cosmoport.core.persistence.*;
import com.google.common.eventbus.EventBus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/t_events")
public final class TimeEventsEndpoint {
    private final EventTypePersistenceService eventTypePersistenceService;
    private final EventTypeCategoryPersistenceService eventTypeCategoryPersistenceService;
    private final EventStatusPersistenceService eventStatusPersistenceService;
    private final EventStatePersistenceService eventStatePersistenceService;
    private final EventDestinationPersistenceService eventDestinationPersistenceService;
    private final EventBus eventBus;

    public TimeEventsEndpoint(EventTypePersistenceService eventTypePersistenceService,
                              EventTypeCategoryPersistenceService eventTypeCategoryPersistenceService,
                              EventStatusPersistenceService eventStatusPersistenceService,
                              EventStatePersistenceService eventStatePersistenceService,
                              EventDestinationPersistenceService eventDestinationPersistenceService,
                              EventBus eventBus) {
        this.eventTypePersistenceService = eventTypePersistenceService;
        this.eventTypeCategoryPersistenceService = eventTypeCategoryPersistenceService;
        this.eventStatusPersistenceService = eventStatusPersistenceService;
        this.eventStatePersistenceService = eventStatePersistenceService;
        this.eventDestinationPersistenceService = eventDestinationPersistenceService;
        this.eventBus = eventBus;
    }

    @GetMapping("/reference_data")
    public EventReferenceDataDto getEventReferenceData() {
        return new EventReferenceDataDto(
                eventTypePersistenceService.getAll(),
                eventTypeCategoryPersistenceService.getAll(),
                eventStatusPersistenceService.getAll(),
                eventStatePersistenceService.getAll(),
                eventDestinationPersistenceService.getAll());
    }

    @GetMapping("/types")
    public List<EventTypeDto> getEventTypes() {
        return eventTypePersistenceService.getAll();
    }

    @PostMapping("/types")
    public EventTypeSaveResultDto createEventType(@RequestBody CreateEventTypeRequestDto eventType) {
        return eventTypePersistenceService.save(eventType);
    }

    @DeleteMapping("/types/{id}")
    public String delete(@PathVariable("id") long id) {
        final String result = "{\"deleted\": " + eventTypePersistenceService.delete(id) + '}';
        eventBus.post(new ReloadMessage());
        return result;
    }

    @PostMapping("/types/categories")
    public EventTypeCategoryDto createEventTypeCategory(@RequestBody CreateEventTypeCategoryRequestDto cat) {
        return eventTypeCategoryPersistenceService.create(cat);
    }

    @GetMapping("/statuses")
    public List<EventStatusDto> getEventStatuses() {
        return eventStatusPersistenceService.getAll();
    }

    @GetMapping("/states")
    public List<EventStateDto> getEventStates() {
        return eventStatePersistenceService.getAll();
    }

    @GetMapping("/destinations")
    public List<EventDestinationDto> getEventDestinations() {
        return eventDestinationPersistenceService.getAll();
    }
}
