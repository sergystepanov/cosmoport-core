package com.cosmoport.core.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TextValueUpdateRequestDto(String text) {
    @JsonCreator
    public TextValueUpdateRequestDto(@JsonProperty("text") String text) {
        this.text = text;
    }
}
