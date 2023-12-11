package com.cosmoport.core.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateEventSubTypeRequestDto(String name, String description) {
    @JsonCreator
    public CreateEventSubTypeRequestDto(@JsonProperty("name") String name, @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }
}
