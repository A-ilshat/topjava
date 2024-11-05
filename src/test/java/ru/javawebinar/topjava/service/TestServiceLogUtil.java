package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestServiceLogUtil {
    private static final Logger log = LoggerFactory.getLogger(TestServiceLogUtil.class);

    public static void logInfo(String profileName) {
        log.info("Test with {} profile", profileName);
    }
}