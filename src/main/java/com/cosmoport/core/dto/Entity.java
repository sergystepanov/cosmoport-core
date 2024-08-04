package com.cosmoport.core.dto;

/**
 * Super type for database entities.
 *
 * @since 0.1.0
 */
public class Entity {
    protected long id;

    protected Entity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
