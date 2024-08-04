package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public final class EventStateDto extends Entity {
    private final long i18nState;

    public EventStateDto(@JsonAlias("id") final long id, @JsonAlias("i18n_state") long i18nState) {
        this.id = id;
        this.i18nState = i18nState;
    }

    public long getI18nState() {
        return i18nState;
    }
}
