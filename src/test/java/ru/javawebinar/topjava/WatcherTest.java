package ru.javawebinar.topjava;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WatcherTest extends TestWatcher {
    private final Logger log = LoggerFactory.getLogger(WatcherTest.class);
    private String testName;

    @Rule
    public final TestRule watcher = new TestWatcher() {
        private long startTime;

        @Override
        protected void starting(Description description) {
            startTime = System.currentTimeMillis();
        }

        @Override
        protected void finished(Description description) {
            long endTime = System.currentTimeMillis();
            long testTime = (endTime - startTime);
            log.info("Test name = {}, completed for time = {}(ms)", testName, testTime);
        }
    };

    public void setTestName(String testName) {
        this.testName = testName;
    }
}