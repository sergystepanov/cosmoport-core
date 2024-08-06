package com.cosmoport.core.config;

import com.cosmoport.core.node.NodesHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountersConfiguration {
    @Bean
    public NodesHolder nodesHolder() {
        return new NodesHolder();
    }
}
