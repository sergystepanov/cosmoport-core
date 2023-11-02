package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A simple translation data object.
 *
 * @since 0.1.0
 */
public record TranslationLightDto(long id, List<String> values) {
    @JsonCreator
    public TranslationLightDto(@JsonProperty("id") long id, @JsonProperty("values") List<String> values) {
        this.id = id;
        this.values = values;
    }

    @Override
    public String toString() {
        return "TranslationDto{" + "id=" + id + ", values=" + values + '}';
    }
}
