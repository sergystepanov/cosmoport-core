package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TimeSuggestionDto(int time) {
    @JsonCreator
    public TimeSuggestionDto(@JsonProperty("time") int time) {
        this.time = time;
    }
}
