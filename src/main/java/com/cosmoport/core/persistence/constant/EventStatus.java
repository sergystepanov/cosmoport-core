package com.cosmoport.core.persistence.constant;

public enum EventStatus {
    OPENED(1),
    CLOSED(2),
    CANCELED(3),
    BOARDING(4),
    DEPARTED(5),
    RETURNED(6),
    PREORDER(7);

    private final int value;

    EventStatus(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
