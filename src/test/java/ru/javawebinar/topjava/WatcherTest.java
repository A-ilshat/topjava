package ru.javawebinar.topjava;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class WatcherTest extends Stopwatch {
    private static final Logger log = LoggerFactory.getLogger(WatcherTest.class);
    private static long totalTime = 0;
    private static Description description;

    private static Description getDescription() {
        return description;
    }

    private void setDescription(Description description) {
        WatcherTest.description = description;
    }

    @Override
    protected void finished(long nanos, Description description) {
        logTestInfo(description, nanos);
    }

    private void logTestInfo(Description description, long nanos) {
        totalTime += nanos;
        setDescription(description);
        log.info("TEST METHOD NAME: {}, TIME = {}(ms)", description.getMethodName(),
                TimeUnit.NANOSECONDS.toMillis(nanos));
    }

    @ClassRule
    public static final ExternalResource resource = new ExternalResource() {
        @Override
        protected void after() {
            log.info("TEST CLASS NAME: {}, TOTAL TIME = {}(ms)", getDescription().getClassName(),
                    TimeUnit.NANOSECONDS.toMillis(totalTime));
        }
    };
}