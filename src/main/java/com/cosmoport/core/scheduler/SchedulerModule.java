package com.cosmoport.core.scheduler;

import com.cosmoport.core.scheduler.job.SomeJob;
import com.google.inject.AbstractModule;

public class SchedulerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SomeJob.class).asEagerSingleton();
    }
}
