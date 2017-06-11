package com.cosmoport.core.persistence.constant;

public enum EventState {
    OPENED(1),
    CLOSED(2);

    private final int value;

    EventState(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
