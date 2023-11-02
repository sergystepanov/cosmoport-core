package com.cosmoport.core.dto;

import com.cosmoport.core.dto.request.HasAuthKey;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SyncTicketsDto(String key, long eventId, int value, String timestamp) implements HasAuthKey {
    @JsonCreator
    public SyncTicketsDto(@JsonProperty("key") String key,
                          @JsonProperty("event_id") long eventId,
                          @JsonProperty("value") int value,
                          @JsonProperty("timestamp") String timestamp) {
        this.key = key;
        this.eventId = eventId;
        this.value = value;
        this.timestamp = timestamp;
    }
}
