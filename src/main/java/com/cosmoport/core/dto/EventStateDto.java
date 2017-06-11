package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class EventStateDto extends Entity implements Serializable {
    private static final long serialVersionUID = -7927071775633946767L;

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
