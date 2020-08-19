package ru.javawebinar.topjava.web.validation.service;


import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.validation.FieldUniqueValidator;

import java.util.Objects;

@Component
public class UserUniqueEmailValidationService implements FieldUniqueValidator {

    final UserService service;

    public UserUniqueEmailValidationService(UserService service) {
        this.service = service;
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) {
        Objects.requireNonNull(fieldName);
        if (!fieldName.equals("email")) {
            throw new UnsupportedOperationException("Field name not supported");
        }
        if (value == null) {
            return false;
        }
        try {
            return service.getByEmail(value.toString()) != null;
        } catch (NotFoundException notFoundException) {
            return false;
        }
    }
}
