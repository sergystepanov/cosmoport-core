package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * A simple translation data object.
 *
 * @since 0.1.0
 */
public final class TranslationLightDto implements Serializable {
    private static final long serialVersionUID = 6579459848250643959L;

    private final long id;
    private final List<String> values;

    @JsonCreator
    public TranslationLightDto(@JsonProperty("id") long id, @JsonProperty("values") List<String> values) {
        this.id = id;
        this.values = values;
    }

    public long getId() {
        return id;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "TranslationDto{" + "id=" + id + ", values=" + values + '}';
    }
}
