package com.cosmoport.core.dto;

import com.cosmoport.core.dto.request.HasAuthKey;

public record SyncAddEventDto(String key, EventDto event, String timestamp) implements HasAuthKey {
    public SyncAddEventDto(String key, EventDto event, String timestamp) {
        this.key = key;
        this.event = event;
        this.timestamp = timestamp;
    }

    @Override
    public String key() {
        return null;
    }
}
