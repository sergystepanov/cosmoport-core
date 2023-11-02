package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class EventStateDto extends Entity {
    private final long i18nState;

    @JsonCreator
    public EventStateDto(@JsonProperty("id") final long id, @JsonProperty("i18n_state") long i18nState) {
        this.id = id;
        this.i18nState = i18nState;
    }

    public long getI18nState() {
        return i18nState;
    }
}
