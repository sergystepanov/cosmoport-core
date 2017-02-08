package com.cosmoport.core.module;

import com.google.inject.AbstractModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The SLF4J logger module which adds it to the injection context.
 *
 * @since 0.0.1
 */
public class LoggerModule extends AbstractModule {
    private final Logger logger;

    public LoggerModule(Logger logger) {
        this.logger = checkNotNull(logger, "logger");
    }

    /**
     * @param loggerClass A class to get logger for.
     * @since 1.0.0
     */
    public LoggerModule(Class<?> loggerClass) {
        this(LoggerFactory.getLogger(checkNotNull(loggerClass, "loggerClass")));
    }

    @Override
    protected void configure() {
        bind(Logger.class).toInstance(logger);
    }
}
