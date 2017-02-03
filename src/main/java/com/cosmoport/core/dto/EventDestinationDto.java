package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class EventDestinationDto extends Entity implements Serializable {
    private final String eventDestinationName;

    @JsonCreator
    public EventDestinationDto(@JsonProperty("event_destination_name") String eventDestinationName) {
        this.eventDestinationName = eventDestinationName;
    }

    public String getEventDestinationName() {
        return eventDestinationName;
    }
}
