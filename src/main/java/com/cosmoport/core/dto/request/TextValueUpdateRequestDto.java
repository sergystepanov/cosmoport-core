package com.cosmoport.core.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class TextValueUpdateRequestDto implements Serializable {
    private static final long serialVersionUID = 6709873680040848475L;

    private final String text;

    @JsonCreator
    public TextValueUpdateRequestDto(@JsonProperty("text") String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}