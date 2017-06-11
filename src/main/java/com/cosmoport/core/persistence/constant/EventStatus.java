package com.cosmoport.core.persistence.constant;

public enum EventStatus {
    CANCELED(1),
    BOARDING(2),
    DEPARTED(3),
    RETURN(4),
    RETURNED(5),
    PREORDER(6);

    private final int value;

    EventStatus(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
