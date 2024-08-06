package com.cosmoport.core.dto;

import com.cosmoport.core.dto.request.HasAuthKey;

public record SyncAddEventDto(String key, EventDto event, String timestamp) implements HasAuthKey {

    @Override
    public String key() {
        return null;
    }
}
