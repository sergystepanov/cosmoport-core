package com.cosmoport.core.config;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class EventBusConfiguration {
    @Bean
    @Scope(BeanDefinition.SCOPE_SINGLETON)
    public EventBus eventBus() {
        return new EventBus();
    }
}
