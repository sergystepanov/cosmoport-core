package com.cosmoport.core.dto;

import java.util.List;

/**
 * A simple translation data object.
 *
 * @since 0.1.0
 */
public record TranslationLightDto(long id, List<String> values) {
    public TranslationLightDto(long id, List<String> values) {
        this.id = id;
        this.values = values;
    }

    @Override
    public String toString() {
        return "TranslationDto{" + "id=" + id + ", values=" + values + '}';
    }
}
