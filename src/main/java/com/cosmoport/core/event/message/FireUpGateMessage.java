package com.cosmoport.core.event.message;

import com.cosmoport.core.dto.EventDto;

public final class FireUpGateMessage {
    private static final char div = '|';
    private static final String token = ":fire_gate";
    private final EventDto event;
    private final String type;

    public FireUpGateMessage(EventDto event, String type) {
        this.event = event;
        this.type = type;
    }

    @Override
    public String toString() {
        return token + div + this.event.getGateId() + div + this.event.getGate2Id() +
                div + this.event.getId() + div + this.type;
    }
}
