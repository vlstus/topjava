package ru.javawebinar.topjava.web.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        try {
            return LocalTime.parse(text, DateTimeFormatter.ISO_TIME/* new DateTimeFormatterBuilder()
                    .append(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toFormatter(locale)*/);
        } catch (DateTimeParseException e) {
            throw new ParseException(e.getMessage(), e.getErrorIndex());
        }
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object.format(DateTimeFormatter.ISO_TIME/*new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toFormatter(locale)*/);
    }
}
