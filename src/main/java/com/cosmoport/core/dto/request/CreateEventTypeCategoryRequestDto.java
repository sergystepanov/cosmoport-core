package com.cosmoport.core.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateEventTypeCategoryRequestDto(String name) {
    @JsonCreator
    public CreateEventTypeCategoryRequestDto(@JsonProperty("name") String name) {
        this.name = name;
    }
}
