package ru.javawebinar.topjava.web.exception.message.factory;

import org.springframework.validation.BindException;
import ru.javawebinar.topjava.web.exception.message.formatter.BindExceptionMessageFormatterStrategy;
import ru.javawebinar.topjava.web.exception.message.formatter.DefaultExceptionMessageFormatterStrategy;
import ru.javawebinar.topjava.web.exception.message.formatter.ExceptionMessageFormatterStrategy;

public class ExceptionMessageFormatterFactory {
    public static ExceptionMessageFormatterStrategy getFormatterStrategy(Class<?> clazz) {
        if (BindException.class.isAssignableFrom(clazz)) {
            return new BindExceptionMessageFormatterStrategy();
        } else {
            return new DefaultExceptionMessageFormatterStrategy();
        }
    }
}
