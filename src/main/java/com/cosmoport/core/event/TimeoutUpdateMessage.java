package com.cosmoport.core.event;

import org.springframework.context.ApplicationEvent;

import java.io.Serial;

public final class TimeoutUpdateMessage extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = -5418408431565956052L;

    public final static String token = ":timeout_update";

    public TimeoutUpdateMessage(Object source) {
        super(source);
    }
}
