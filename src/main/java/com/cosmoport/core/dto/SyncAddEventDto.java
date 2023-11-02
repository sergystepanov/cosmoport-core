package com.cosmoport.core.dto;

import com.cosmoport.core.dto.request.HasAuthKey;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SyncAddEventDto(String key, EventDto event, String timestamp) implements HasAuthKey {
    @JsonCreator
    public SyncAddEventDto(@JsonProperty("key") String key,
                           @JsonProperty("event") EventDto event,
                           @JsonProperty("timestamp") String timestamp) {
        this.key = key;
        this.event = event;
        this.timestamp = timestamp;
    }

    @Override
    public String key() {
        return null;
    }
}
