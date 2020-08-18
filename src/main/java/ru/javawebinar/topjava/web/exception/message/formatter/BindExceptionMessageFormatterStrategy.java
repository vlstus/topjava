package ru.javawebinar.topjava.web.exception.message.formatter;

import org.springframework.validation.BindException;
import ru.javawebinar.topjava.util.ValidationUtil;

public class BindExceptionMessageFormatterStrategy implements ExceptionMessageFormatterStrategy {
    @Override
    public String formatMessage(Throwable e) {
        return ValidationUtil.getErrorResponseAsString(((BindException) e).getBindingResult());
    }
}
