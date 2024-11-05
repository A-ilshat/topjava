package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WatcherTestUtil {
    private static final Logger log = LoggerFactory.getLogger(WatcherTestUtil.class);
    private static final List<String> logList = new ArrayList<>();

    public static final Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            logTestInfo(description, nanos);
        }

        private void logTestInfo(Description description, long nanos) {
            String formattedLogInfo = formattedLogInfo(description, nanos);
            log.info(formattedLogInfo);
            logList.add(formattedLogInfo);
        }

        private String formattedLogInfo(Description description, long nanos) {
            int testNameLength = description.getMethodName().length();
            StringBuilder testInfoBuilder = new StringBuilder()
                    .append(description.getMethodName())
                    .append("-----------------------------")
                    .delete(testNameLength, (testNameLength + testNameLength))
                    .append(TimeUnit.NANOSECONDS.toMillis(nanos))
                    .append("(ms) \n");

            //will be displayed "test name----------------------00(ms)"
            return testInfoBuilder.toString();
        }
    };

    public static final ExternalResource resource = new ExternalResource() {
        @Override
        protected void after() {
            StringBuilder builder = new StringBuilder();
            logList.forEach(builder::append);
            log.info("\n{}", builder);
        }
    };
}
