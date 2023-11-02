package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ProxyRequestDto(String name, EventDto event, String type) {
    @JsonCreator
    public ProxyRequestDto(@JsonProperty("name") String name,
                           @JsonProperty("event") EventDto event,
                           @JsonProperty("type") String type) {
        this.name = name;
        this.event = event;
        this.type = type;
    }
}
