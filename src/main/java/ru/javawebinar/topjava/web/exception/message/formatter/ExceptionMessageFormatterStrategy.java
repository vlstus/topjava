package ru.javawebinar.topjava.web.exception.message.formatter;

public interface ExceptionMessageFormatterStrategy {
    String formatMessage(Throwable e);
}
