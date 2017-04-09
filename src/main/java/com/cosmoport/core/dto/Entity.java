package com.cosmoport.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Super type for database entities.
 *
 * @since 0.1.0
 */
public class Entity {
    protected long id;

    protected Entity() {
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }
}
