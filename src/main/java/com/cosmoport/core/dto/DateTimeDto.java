package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DateTimeDto(long timestamp) {
    @JsonCreator
    public DateTimeDto(@JsonProperty("timestamp") long timestamp) {
        this.timestamp = timestamp;
    }
}
