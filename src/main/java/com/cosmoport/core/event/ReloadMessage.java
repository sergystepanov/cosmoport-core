package com.cosmoport.core.event;

import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * The message class for notification of the clients.
 *
 * @since 0.1.0
 */
public final class ReloadMessage extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = -5418408431565956052L;

    public static final String token = ":reload";

    public ReloadMessage(Object source) {
        super(source);
    }
}
