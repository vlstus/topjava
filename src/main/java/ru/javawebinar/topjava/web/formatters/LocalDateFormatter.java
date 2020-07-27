package ru.javawebinar.topjava.web.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        try {
            return LocalDate.parse(text, DateTimeFormatter.ISO_DATE/*new DateTimeFormatterBuilder()
                    .append(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toFormatter(locale)*/);
        } catch (DateTimeParseException e) {
            throw new ParseException(e.getMessage(), e.getErrorIndex());
        }
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.format
                (DateTimeFormatter.ISO_DATE/*new DateTimeFormatterBuilder()
                        .append(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).toFormatter(locale)*/);
    }
}
