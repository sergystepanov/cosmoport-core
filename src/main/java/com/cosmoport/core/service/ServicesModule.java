package com.cosmoport.core.service;

import com.google.inject.AbstractModule;

public final class ServicesModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(SuggestionService.class);
    }
}
