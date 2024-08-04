package com.cosmoport.core.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public record CreateEventTypeRequestDto(long categoryId, String name, String description,
                                        List<CreateEventSubTypeRequestDto> subtypes, int defaultDuration,
                                        int defaultRepeatInterval, double defaultCost) {
    public CreateEventTypeRequestDto(@JsonAlias("category_id") long categoryId,
                                     String name,
                                     String description,
                                     List<CreateEventSubTypeRequestDto> subtypes,
                                     @JsonAlias("default_duration") int defaultDuration,
                                     @JsonAlias("default_repeat_interval") int defaultRepeatInterval,
                                     @JsonAlias("default_cost") double defaultCost) {
        this.categoryId = categoryId;
        // trim
        this.name = (name != null) ? name.trim() : null;
        this.description = description;
        this.subtypes = subtypes;
        this.defaultDuration = defaultDuration;
        this.defaultRepeatInterval = defaultRepeatInterval;
        this.defaultCost = defaultCost;
    }
}
