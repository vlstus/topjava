package ru.javawebinar.topjava.web.validation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.validation.FieldUniqueValidator;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class MealServiceUniqueValidator implements FieldUniqueValidator {

    @Autowired
    MealService service;

    @Override
    public boolean fieldValueExists(Object value, String fieldName) {
        Objects.requireNonNull(fieldName);
        if (!fieldName.equals("dateTime")) {
            throw new UnsupportedOperationException("Field name not supported");
        }
        if (value == null) {
            return false;
        }
        return service.getAll(SecurityUtil.get().getId()).stream()
                .anyMatch(meal -> meal.getDateTime().equals(LocalDateTime.parse(value.toString())));

    }
}
