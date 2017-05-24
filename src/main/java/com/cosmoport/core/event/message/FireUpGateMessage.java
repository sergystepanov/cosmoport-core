package com.cosmoport.core.event.message;

public final class FireUpGateMessage {
    private static final String token = ":fire_gate";
    private final long id;

    public FireUpGateMessage(final long id) {
        this.id = id;
    }

    public String token() {
        return token + "|" + this.id;
    }
}
