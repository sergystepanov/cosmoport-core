package com.cosmoport.core.scheduler.job;

import com.google.inject.Singleton;
import de.skuzzle.inject.async.schedule.ScheduledContext;
import de.skuzzle.inject.async.schedule.annotation.Scheduled;
import de.skuzzle.inject.async.schedule.annotation.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class SomeJob {
    private static final Logger logger = LoggerFactory.getLogger(SomeJob.class.getCanonicalName());
    private static final AtomicInteger counter = new AtomicInteger(0);

    // @Inject some service

    @Scheduled
    @SimpleTrigger(value = 5, initialDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void executePeriodic(ScheduledContext context) {
        logger.info("\u001B[35m[JOB]\u001B[0m {} -> test", System.currentTimeMillis());

        if (counter.intValue() < 5) {
            logger.info("counter: {}", counter);
        }

        if (counter.intValue() > 5) {
            context.cancel(true);
        }

        counter.incrementAndGet();
    }
}
