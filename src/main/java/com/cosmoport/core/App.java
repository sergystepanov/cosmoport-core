package com.cosmoport.core;

import com.cosmoport.core.api.ApiV0Module;
import com.cosmoport.core.api.error.ApiExceptionMapper;
import com.cosmoport.core.config.Config;
import com.cosmoport.core.event.MessageHub;
import com.cosmoport.core.module.JsonModule;
import com.cosmoport.core.module.LoggerModule;
import com.cosmoport.core.persistence.module.PersistenceModule;
import com.cosmoport.core.socket.EventServlet;
import com.cosmoport.core.socket.EventSocket;
import com.cosmoport.core.statics.StaticsEndpoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.eventbus.EventBus;
import com.google.inject.*;
import com.google.inject.servlet.ServletModule;
import com.palominolabs.http.server.HttpServerWrapperModule;
import de.skuzzle.inject.async.GuiceAsync;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.interceptors.GZIPEncodingInterceptor;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.LogManager;

public class App {
    public static void main(String[] args) throws Exception {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        Injector injector = Guice.createInjector(new ServiceModule());

        injector.getAllBindings();

        injector.createChildInjector().getAllBindings();

        Server server = new Server(Config.PORT);
        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addEventListener(injector.getInstance(GuiceResteasyBootstrapServletContextListener.class));

        ServletHolder sh = new ServletHolder(HttpServletDispatcher.class);
        servletHandler.addServlet(sh, "/*");

        // Add a websocket to a specific path spec
        ServletHolder holderEvents = new ServletHolder("ws-events", EventServlet.class);
        servletHandler.addServlet(holderEvents, "/events/*");

        server.setHandler(servletHandler);
        server.start();
        server.join();
    }

    private static class ServiceModule extends AbstractModule {
        final EventBus eventBus = new EventBus("Default EventBus");

        @Override
        protected void configure() {
            binder().requireExplicitBindings();

            GuiceAsync.enableFor(binder());

            install(new HttpServerWrapperModule());
            install(new JsonModule());
            install(new LoggerModule(App.class));
            install(new ApiV0Module());
            install(new PersistenceModule());
//            install(new SchedulerModule());

            bind(EventBus.class).toInstance(eventBus);
            eventBus.register(new MessageHub());
            eventBus.register(new EventSocket());

            bind(StaticsEndpoint.class).asEagerSingleton();

            bind(GuiceResteasyBootstrapServletContextListener.class);
            bind(ApiExceptionMapper.class);
            bind(GZIPEncodingInterceptor.class);

            install(new ServletModule() {
                @Override
                protected void configureServlets() {
                    bind(HttpServletDispatcher.class).in(Scopes.SINGLETON);
                    serve("/*").with(HttpServletDispatcher.class);
                }
            });
        }

        @Provides
        @Singleton
        JacksonJsonProvider getJacksonJsonProvider(ObjectMapper objectMapper) {
            return new JacksonJsonProvider(objectMapper);
        }
    }
}
