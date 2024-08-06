package com.cosmoport.core.event;

import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * The message class for notification of the clients.
 *
 * @since 0.1.0
 */
public final class SyncTimetablesMessage extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = -5418408431565956052L;

    public static final String token = ":sync_timetables";

    public SyncTimetablesMessage(Object source) {
        super(source);
    }
}
