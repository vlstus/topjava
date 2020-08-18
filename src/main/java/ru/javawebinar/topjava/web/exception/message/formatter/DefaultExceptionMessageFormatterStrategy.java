package ru.javawebinar.topjava.web.exception.message.formatter;

public class DefaultExceptionMessageFormatterStrategy implements ExceptionMessageFormatterStrategy {
    @Override
    public String formatMessage(Throwable e) {
        return e.toString();
    }
}
