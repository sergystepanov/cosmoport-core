package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class DateTimeDto implements Serializable {
    private static final long serialVersionUID = 164282638869513917L;

    private final long timestamp;

    @JsonCreator
    public DateTimeDto(@JsonProperty("timestamp") long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
