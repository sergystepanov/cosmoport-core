package com.cosmoport.core.socket;

import com.cosmoport.core.event.FireUpGateMessage;
import com.cosmoport.core.event.ReloadMessage;
import com.cosmoport.core.event.SyncTimetablesMessage;
import com.cosmoport.core.event.TimeoutUpdateMessage;
import com.cosmoport.core.node.NodesHolder;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class WebsocketHandler extends TextWebSocketHandler {
    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
    private final Logger logger;
    private final NodesHolder nodesHolder;

    public WebsocketHandler(Logger logger, NodesHolder nodesHolder) {
        this.logger = logger;
        this.nodesHolder = nodesHolder;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        logger.info("Socket Connected: {}", session.getRemoteAddress());
        logger.info("params: {}", session.getAttributes());

        // Get what connected
        nodesHolder.incGates();
        nodesHolder.incTables();

        sendAll(":update-nodes:");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        logger.info("Socket closed: [{}]", status);

        try {
            super.afterConnectionClosed(session, status);
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
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        pub(message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.error("{} - {}", session, exception.getMessage());
    }

    @EventListener
    public void onReloadMessage(ReloadMessage m) {
        logger.info("[socket] {}", ReloadMessage.token);
        sendAll(ReloadMessage.token);
    }

    @EventListener
    public void onTimeoutUpdateMessage(TimeoutUpdateMessage m) {
        logger.info("[socket] {}", TimeoutUpdateMessage.token);
        sendAll(TimeoutUpdateMessage.token);
    }

    @EventListener
    public void onSyncTimetablesMessage(SyncTimetablesMessage m) {
        logger.info("[socket] {}", SyncTimetablesMessage.token);
        sendAll(SyncTimetablesMessage.token);
    }

    @EventListener
    public void onFireGateMessage(FireUpGateMessage m) {
        logger.info("[socket] {}", m.toString());
        sendAll(m.toString());
    }

    /**
     * Send a message to all websocket clients.
     *
     * @param message A message to send.
     * @since 0.1.0
     */
    private void sendAll(final String message) {
        pub(new TextMessage(message));
    }

    private void pub(TextMessage message) {
        synchronized (sessions) {
            sessions.stream()
                    .filter(WebSocketSession::isOpen)
                    .forEach(session -> {
                        try {
                            session.sendMessage(message);
                        } catch (Exception e) {
                            logger.error(
                                    "[socket] Couldn't send a message {} for {}", message, session.getRemoteAddress());
                        }
                    });
        }
    }
}
