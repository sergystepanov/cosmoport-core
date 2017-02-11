package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @since 0.1.0
 */
public final class TranslationDto implements Serializable {
    private static final long serialVersionUID = 6579459848250643959L;

    private final long id;
    private final List<String> values;

    @JsonCreator
    public TranslationDto(@JsonProperty("id") long id, @JsonProperty("values") List<String> values) {
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
        return "TranslationDto{" +
                "id=" + id +
                ", values=" + values +
                '}';
    }
}
