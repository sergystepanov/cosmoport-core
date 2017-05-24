package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class ProxyRequestDto implements Serializable {
    private static final long serialVersionUID = -3988557988923409746L;

    private final String name;
    private final EventDto event;

    @JsonCreator
    public ProxyRequestDto(@JsonProperty("name") String name, @JsonProperty("event") EventDto event) {
        this.name = name;
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public EventDto getEvent() {
        return event;
    }
}
