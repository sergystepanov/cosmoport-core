package com.cosmoport.core.dto;

import com.cosmoport.core.dto.request.HasAuthKey;
import com.fasterxml.jackson.annotation.JsonAlias;

public record SyncTicketsDto(String key, long eventId, int value, String timestamp) implements HasAuthKey {
    public SyncTicketsDto(String key,
                          @JsonAlias("event_id") long eventId,
                          int value,
                          String timestamp) {
        this.key = key;
        this.eventId = eventId;
        this.value = value;
        this.timestamp = timestamp;
    }
}
