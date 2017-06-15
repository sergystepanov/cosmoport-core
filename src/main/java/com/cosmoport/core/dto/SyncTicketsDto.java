package com.cosmoport.core.dto;

import com.cosmoport.core.dto.request.HasAuthKey;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class SyncTicketsDto implements HasAuthKey {
    private final String key;
    private final long eventId;
    private final int value;
    private final String timestamp;

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

    public String getKey() {
        return key;
    }

    public long getEventId() {
        return eventId;
    }

    public int getValue() {
        return value;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
