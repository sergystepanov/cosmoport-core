package com.cosmoport.core.event;

import com.cosmoport.core.dto.EventDto;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

public final class FireUpGateMessage extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = -5418408431565956052L;

    private static final char div = '|';
    private static final String token = ":fire_gate";
    private final EventDto event;
    private final String type;

    public FireUpGateMessage(Object source, EventDto event, String type) {
        super(source);
        this.event = event;
        this.type = type;
    }

    @Override
    public String toString() {
        return token + div + this.event.getGateId() + div + this.event.getGate2Id() +
                div + this.event.getId() + div + this.type;
    }
}
