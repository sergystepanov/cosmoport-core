package com.cosmoport.core.dto;

public record TimeSuggestionDto(int time) {
    public TimeSuggestionDto(int time) {
        this.time = time;
    }
}
