package com.cosmoport.core.socket;

import com.cosmoport.core.event.message.FireUpGateMessage;
import com.cosmoport.core.event.message.ReloadMessage;
import com.cosmoport.core.event.message.SyncTimetablesMessage;
import com.cosmoport.core.event.message.TimeoutUpdateMessage;
import com.cosmoport.core.node.NodesHolder;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            sessions.stream()
                    .filter(Session::isOpen)
                    .forEach(session -> {
                        try {
                            session.getRemote().sendString(message);
                        } catch (Exception e) {
                            logger.error(
                                    "[socket] Couldn't send a message {} for {}", message, session.getRemoteAddress());
                        }
                    });
        }
    }

    @Override
    public void onWebSocketText(final String message) {
        super.onWebSocketText(message);

        logger.info("Received TEXT message: {}", message);
        sendAll("text");
    }

    @Subscribe
    public void onReloadMessage(final ReloadMessage message) {
        logger.info("[socket] {}", ReloadMessage.token);
        sendAll(ReloadMessage.token);
    }

    @Subscribe
    public void onTimeoutUpdateMessage(final TimeoutUpdateMessage message) {
        logger.info("[socket] {}", TimeoutUpdateMessage.token);
        sendAll(TimeoutUpdateMessage.token);
    }

    @Subscribe
    public void onSyncTimetablesMessage(final SyncTimetablesMessage message) {
        logger.info("[socket] {}", SyncTimetablesMessage.token);
        sendAll(SyncTimetablesMessage.token);
    }

    @Subscribe
    public void onFireGateMessage(final FireUpGateMessage message) {
        logger.info("[socket] {}", message.token());
        sendAll(message.token());
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        sessions.remove(getSession());
        logger.info("Socket closed: [{}] {}", statusCode, reason);

        try {
            super.onWebSocketClose(statusCode, reason);
        } catch (Exception e) {
            logger.error("Socket closed with an error: {}", e.getMessage());
        } finally {
            // Get what connected
            nodesHolder.decGates();
            nodesHolder.decTables();

            sendAll(":update-nodes:");
        }
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);

        cause.printStackTrace(System.err);
    }
}
