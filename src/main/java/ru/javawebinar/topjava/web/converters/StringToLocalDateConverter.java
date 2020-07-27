package ru.javawebinar.topjava.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public @Nullable LocalDate convert(String source) {
        return source.isEmpty() ? null : LocalDate.parse(source, DateTimeFormatter.ISO_DATE);
    }
}
