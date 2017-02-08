package com.cosmoport.core.api;

import com.cosmoport.core.db.DatasourceServiceProvider;
import com.cosmoport.core.file.FileSystem;
import com.cosmoport.core.persistence.*;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

import javax.sql.DataSource;

public class ApiV0Module extends AbstractModule {
    @Override
    protected void configure() {
        // Filesystem
        bindConstant().annotatedWith(Names.named("fileSystemRoot")).to(System.getProperty("user.dir"));
        bind(FileSystem.class).asEagerSingleton();

        // Database
        bind(DataSource.class).toProvider(DatasourceServiceProvider.class).in(Scopes.SINGLETON);

        bind(TestPersistenceService.class);
        bind(TestResource.class).asEagerSingleton();
        bind(DateTimeEndpoint.class).asEagerSingleton();
        bind(TimetableEndpoint.class).asEagerSingleton();
        bind(TimetablePersistenceService.class);
        bind(TimeEventsEndpoint.class).asEagerSingleton();
        bind(EventTypePersistenceService.class);
        bind(EventStatusPersistenceService.class);
        bind(EventDestinationPersistenceService.class);
        bind(EventPersistenceService.class);
    }
}
