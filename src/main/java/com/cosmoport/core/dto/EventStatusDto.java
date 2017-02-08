package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class EventStatusDto extends Entity implements Serializable {
    private final String status;

    @JsonCreator
    public EventStatusDto(@JsonProperty("id") long id, @JsonProperty("status") String status) {
        this.id = id;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
