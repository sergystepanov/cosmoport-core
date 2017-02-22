package com.cosmoport.core.socket;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.eclipse.jetty.websocket.servlet.*;

@SuppressWarnings("serial")
public class EventServlet extends WebSocketServlet {
    @Inject
    private Injector injector;

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        // Register your Adapater
        webSocketServletFactory.register(EventSocket.class);

        // Get the current creator (for reuse)
        final WebSocketCreator creator = webSocketServletFactory.getCreator();

        // Set your custom Creator
        webSocketServletFactory.setCreator(new WebSocketCreator() {
            @Override
            public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
                Object webSocket = creator.createWebSocket(servletUpgradeRequest, servletUpgradeResponse);

                // Use the object created by the default creator and inject your members
                injector.injectMembers(webSocket);

                return webSocket;
            }
        });
    }
}
