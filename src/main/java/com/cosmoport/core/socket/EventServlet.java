package com.cosmoport.core.socket;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@SuppressWarnings("serial")
public class EventServlet extends WebSocketServlet {
    @Inject
    private Injector injector;

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(EventSocket.class);

        final WebSocketCreator creator = factory.getCreator();
        // Set your custom Creator
        factory.setCreator((servletUpgradeRequest, servletUpgradeResponse) -> {
            Object webSocket = creator.createWebSocket(servletUpgradeRequest, servletUpgradeResponse);
            injector.injectMembers(webSocket);

            return webSocket;
        });
    }
}
