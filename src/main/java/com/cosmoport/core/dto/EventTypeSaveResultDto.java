package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record EventTypeSaveResultDto(List<EventTypeCategoryDto> eventTypeCategories, List<EventTypeDto> eventTypes) {
    @JsonCreator
    public EventTypeSaveResultDto(
            @JsonProperty("event_type_categories") List<EventTypeCategoryDto> eventTypeCategories,
            @JsonProperty("event_types") List<EventTypeDto> eventTypes) {
        this.eventTypeCategories = eventTypeCategories;
        this.eventTypes = eventTypes;
    }
}
