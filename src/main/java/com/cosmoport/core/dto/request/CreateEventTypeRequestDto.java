package com.cosmoport.core.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateEventTypeRequestDto(String name, String subname, String description, int defaultDuration,
                                        int defaultRepeatInterval, double defaultCost) {
    @JsonCreator
    public CreateEventTypeRequestDto(@JsonProperty("name") String name,
                                     @JsonProperty("subname") String subname,
                                     @JsonProperty("description") String description,
                                     @JsonProperty("default_duration") int defaultDuration,
                                     @JsonProperty("default_repeat_interval") int defaultRepeatInterval,
                                     @JsonProperty("default_cost") double defaultCost) {
        this.name = name;
        this.subname = subname;
        this.description = description;
        this.defaultDuration = defaultDuration;
        this.defaultRepeatInterval = defaultRepeatInterval;
        this.defaultCost = defaultCost;
    }
}
