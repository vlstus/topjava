package ru.javawebinar.topjava.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public @Nullable LocalTime convert(String source) {
        return source.isEmpty() ? null : LocalTime.parse(source, DateTimeFormatter.ISO_TIME);
    }
}
