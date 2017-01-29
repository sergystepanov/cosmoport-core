package com.cosmoport.core.socket;

import com.cosmoport.core.event.message.TestMessage;
import com.google.common.eventbus.Subscribe;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Socket connections handler.
 *
 * @since 0.0.1
 */
public class EventSocket extends WebSocketAdapter {
    private static Logger logger = LoggerFactory.getLogger(EventSocket.class.getCanonicalName());
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

    @Override
    public void onWebSocketConnect(final Session session) {
        super.onWebSocketConnect(session);

        sessions.add(session);
        logger.info("Socket Connected: {} with ttl {} ms", session.getRemoteAddress(), session.getIdleTimeout());
        logger.info("params: {}", session.getUpgradeRequest().getParameterMap());
    }

    @Override
    public void onWebSocketText(final String message) {
        super.onWebSocketText(message);

        logger.info("Received TEXT message: {}", message);

        synchronized(sessions) {
            for (Session s : sessions) {
                if (s.isOpen()) {
                    try {
                        s.getRemote().sendString("Some text");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Subscribe
    public void applicationEvent(TestMessage message) {
        // handle event
        logger.info("test message from socket");

        synchronized(sessions) {
            for (Session s : sessions) {
                if (s.isOpen()) {
                    try {
                        s.getRemote().sendString(":do:");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);

        sessions.remove(getSession());
        logger.info("Socket Closed: [{}] {}", statusCode, reason);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);

        cause.printStackTrace(System.err);
    }
}
