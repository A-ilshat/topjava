package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class WatcherTest extends Stopwatch {
    private final Logger log = LoggerFactory.getLogger(WatcherTest.class);

    @Override
    protected void finished(long nanos, Description description) {
        logTestInfo(description, nanos);
    }

    private void logTestInfo(Description description, long nanos) {
        log.info("TEST NAME: {}, TIME = {}(ms)", description.getMethodName(),
                TimeUnit.NANOSECONDS.toMillis(nanos));
    }
}