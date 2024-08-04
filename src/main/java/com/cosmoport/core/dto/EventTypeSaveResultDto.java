package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record EventTypeSaveResultDto(List<EventTypeCategoryDto> eventTypeCategories, List<EventTypeDto> eventTypes) {
    public EventTypeSaveResultDto(
            @JsonAlias("event_type_categories") List<EventTypeCategoryDto> eventTypeCategories,
            @JsonAlias("event_types") List<EventTypeDto> eventTypes) {
        this.eventTypeCategories = eventTypeCategories;
        this.eventTypes = eventTypes;
    }
}
