package ru.javawebinar.topjava.web.validation.service;

import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.validation.FieldUniqueValidator;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class MealUniqueDateTimeValidationService implements FieldUniqueValidator {

    final MealService service;

    public MealUniqueDateTimeValidationService(MealService service) {
        this.service = service;
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) {
        Objects.requireNonNull(fieldName);
        if (!fieldName.equals("dateTime") && !(value instanceof LocalDateTime)) {
            throw new UnsupportedOperationException("Field name or type not supported");
        }
        if (value == null) {
            return false;
        }
        LocalDateTime fieldValue = (LocalDateTime) value;
        return service.getAll(SecurityUtil.get().getId()).stream()
                .anyMatch(meal -> meal.getDateTime().equals(fieldValue));

    }
}
