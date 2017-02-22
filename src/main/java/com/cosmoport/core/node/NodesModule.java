package com.cosmoport.core.node;

import com.google.inject.AbstractModule;

public class NodesModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(NodesHolder.class).asEagerSingleton();
    }
}
