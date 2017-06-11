package com.cosmoport.core.dto;

import java.io.Serializable;
import java.util.List;

public final class EventReferenceDataDto implements Serializable {
    private static final long serialVersionUID = 7211767910694077239L;

    private final List<EventTypeDto> types;
    private final List<EventStatusDto> statuses;
    private final List<EventStateDto> states;
    private final List<EventDestinationDto> destinations;

    public EventReferenceDataDto(List<EventTypeDto> types,
                                 List<EventStatusDto> statuses,
                                 List<EventStateDto> states,
                                 List<EventDestinationDto> destinations) {
        this.types = types;
        this.statuses = statuses;
        this.states = states;
        this.destinations = destinations;
    }

    public List<EventTypeDto> getTypes() {
        return types;
    }

    public List<EventStatusDto> getStatuses() {
        return statuses;
    }

    public List<EventStateDto> getStates() {
        return states;
    }

    public List<EventDestinationDto> getDestinations() {
        return destinations;
    }
}
