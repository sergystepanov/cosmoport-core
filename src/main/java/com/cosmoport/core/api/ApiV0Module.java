package com.cosmoport.core.api;

import com.cosmoport.core.file.FileSystem;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class ApiV0Module extends AbstractModule {
    @Override
    protected void configure() {
        // Filesystem
        bindConstant().annotatedWith(Names.named("fileSystemRoot")).to(System.getProperty("user.dir"));

        bind(FileSystem.class).asEagerSingleton();

        bind(DateTimeEndpoint.class).asEagerSingleton();
        bind(TimetableEndpoint.class).asEagerSingleton();
        bind(TimeEventsEndpoint.class).asEagerSingleton();
        bind(TranslationEndpoint.class).asEagerSingleton();
        bind(NodesEndpoint.class).asEagerSingleton();
        bind(SettingsEndpoint.class).asEagerSingleton();
    }
}
