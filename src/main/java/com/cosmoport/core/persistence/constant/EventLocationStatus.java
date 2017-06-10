package com.cosmoport.core.persistence.constant;

public enum EventLocationStatus {
    BOARDING(1),
    DEPARTED(2),
    RETURNED(3),
    PREORDER(4),
    RETURN(5);

    private final int value;

    EventLocationStatus(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
