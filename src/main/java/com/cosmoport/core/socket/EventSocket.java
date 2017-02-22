package com.cosmoport.core.socket;

import com.cosmoport.core.event.message.TestMessage;
import com.cosmoport.core.node.NodesHolder;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Socket connections handler.
 *
 * @since 0.1.0
 */
public class EventSocket extends WebSocketAdapter {
    private static Logger logger = LoggerFactory.getLogger(EventSocket.class.getCanonicalName());
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    @Inject
    private NodesHolder nodesHolder;

    @Override
    public void onWebSocketConnect(final Session session) {
        super.onWebSocketConnect(session);

        sessions.add(session);
        logger.info("Socket Connected: {} with ttl {} ms", session.getRemoteAddress(), session.getIdleTimeout());
        logger.info("params: {}", session.getUpgradeRequest().getParameterMap());

        // Get what connected
        nodesHolder.incGates();
        nodesHolder.incTables();

        sendAll(":update-nodes:");
    }

    /**
     * Send a message to all websocket clients.
     *
     * @param message A message to send.
     * @since 0.1.0
     */
    private void sendAll(final String message) {
        synchronized (sessions) {
            for (Session s : sessions) {
                if (s.isOpen()) {
                    try {
                        s.getRemote().sendString(message);
                    } catch (IOException | WebSocketException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onWebSocketText(final String message) {
        super.onWebSocketText(message);

        logger.info("Received TEXT message: {}", message);
        sendAll("text");
    }

    @Subscribe
    public void applicationEvent(TestMessage message) {
        logger.info("test message from socket");
        sendAll(":do:");
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        Session s = getSession();
        sessions.remove(s);
        logger.info("Socket Closed: [{}] {}", statusCode, reason);

        super.onWebSocketClose(statusCode, reason);
        // Get what connected
        nodesHolder.decGates();
        nodesHolder.decTables();

        sendAll(":update-nodes:");
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);

        cause.printStackTrace(System.err);
    }
}
