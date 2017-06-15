package com.cosmoport.core.dto;

import com.cosmoport.core.dto.request.HasAuthKey;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SyncAddEventDto implements HasAuthKey {
    private final String key;
    private final EventDto event;
    private final String timestamp;

    @JsonCreator
    public SyncAddEventDto(@JsonProperty("key") String key,
                           @JsonProperty("event") EventDto event,
                           @JsonProperty("timestamp") String timestamp) {
        this.key = key;
        this.event = event;
        this.timestamp = timestamp;
    }

    public EventDto getEvent() {
        return event;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String getKey() {
        return null;
    }
}
