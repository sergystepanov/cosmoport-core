package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class TimeSuggestionDto implements Serializable {
    private static final long serialVersionUID = -8942460347830274524L;

    private final int time;

    @JsonCreator
    public TimeSuggestionDto(@JsonProperty("time") int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
