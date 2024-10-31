package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WatcherTest {
    private static final Logger log = LoggerFactory.getLogger(WatcherTest.class);
    private static final List<String> logList = new ArrayList<>();

    public static final Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            logTestInfo(description, nanos);
        }

        private void logTestInfo(Description description, long nanos) {
            log.info(formattedLogInfo(description, nanos));
            logList.add(formattedLogInfo(description, nanos));
        }

        private String formattedLogInfo(Description description, long nanos) {
            int charCount = description.getMethodName().length();
            StringBuilder dynamicSymbols = new StringBuilder();
            dynamicSymbols.append("----------------------------")
                    .delete(0, charCount);
            return description.getMethodName() + dynamicSymbols + TimeUnit.NANOSECONDS.toMillis(nanos) + "(ms) \n";
        }
    };

    public static final ExternalResource resource = new ExternalResource() {
        @Override
        protected void after() {
            StringBuilder builder = new StringBuilder();
            logList.forEach(builder::append);
            log.info(String.valueOf(builder));
        }
    };
}