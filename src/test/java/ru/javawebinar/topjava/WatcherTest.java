package ru.javawebinar.topjava;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WatcherTest extends Stopwatch {
    private static final Logger log = LoggerFactory.getLogger(WatcherTest.class);
    private static final List<String> logList = new ArrayList<>();

    @Override
    protected void finished(long nanos, Description description) {
        logTestInfo(description, nanos);
    }

    private void logTestInfo(Description description, long nanos) {
        String logInfo = "TEST NAME: " +
                description.getMethodName() +
                ", TIME: " +
                TimeUnit.NANOSECONDS.toMillis(nanos) +
                "(ms)";

        log.info(logInfo);
        logList.add(logInfo);
    }

    @ClassRule
    public static final ExternalResource resource = new ExternalResource() {
        @Override
        protected void after() {
            logList.forEach(log::info);
        }
    };
}