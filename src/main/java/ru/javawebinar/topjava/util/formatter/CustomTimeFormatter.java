package ru.javawebinar.topjava.util.formatter;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CustomTimeFormatter implements Formatter<LocalTime> {
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalTime parse(@Nullable String text, Locale locale) throws ParseException {
        return LocalTime.parse(text, format);
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object.toString();
    }
}