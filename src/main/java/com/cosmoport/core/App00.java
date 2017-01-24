package com.cosmoport.core;

import com.cosmoport.core.api.ApiV0Module;
import com.cosmoport.core.api.error.ApiExceptionMapper;
import com.cosmoport.core.config.Config;
import com.cosmoport.core.module.JsonModule;
import com.cosmoport.core.module.LoggerModule;
import com.cosmoport.core.scheduler.SchedulerModule;
import com.cosmoport.core.socket.EventServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.*;
import com.google.inject.servlet.ServletModule;
import com.palominolabs.http.server.*;
import de.skuzzle.inject.async.GuiceAsync;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.interceptors.GZIPEncodingInterceptor;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.ServletContextListener;
import java.util.logging.LogManager;

class App00 {
    public static void main(String... args) throws Exception {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        HttpServerWrapperConfig config = new HttpServerWrapperConfig()
                .withLogbackAccessQuiet(true)
                .withAccessLogConfigFileInClasspath(Config.LOGBACK_CONFIG_PATH)
                .withHttpServerConnectorConfig(HttpServerConnectorConfig.forHttp(Config.SERVER_ADDRESS, Config.PORT));

        BinderProviderCapture<? extends ServletContextListener> listenerProvider =
                new BinderProviderCapture<>(GuiceResteasyBootstrapServletContextListener.class);
        config.addServletContextListenerProvider(listenerProvider);

        Injector injector = Guice.createInjector(new ServiceModule(listenerProvider));
        injector.getInstance(HttpServerWrapperFactory.class)
                .getHttpServerWrapper(config)
                .start();
    }

    private static class ServiceModule extends AbstractModule {
        private final BinderProviderCapture<?> listenerProvider;

        ServiceModule(BinderProviderCapture<?> listenerProvider) {
            this.listenerProvider = listenerProvider;
        }

        @Override
        protected void configure() {
            binder().requireExplicitBindings();

            GuiceAsync.enableFor(binder());

            install(new HttpServerWrapperModule());
            install(new JsonModule());
            install(new LoggerModule(App00.class));
            install(new ApiV0Module());
            install(new SchedulerModule());

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

            install(new ServletModule() {
                @Override
                protected void configureServlets() {
                    bind(EventServlet.class).in(Scopes.SINGLETON);
                    serve("/events/*").with(EventServlet.class);
                }
            });

            listenerProvider.saveProvider(binder());
        }

        @Provides
        @Singleton
        JacksonJsonProvider getJacksonJsonProvider(ObjectMapper objectMapper) {
            return new JacksonJsonProvider(objectMapper);
        }
    }
}