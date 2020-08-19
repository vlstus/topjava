package ru.javawebinar.topjava.web.validation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.validation.FieldUniqueValidator;

import java.util.Objects;

@Component
public class UserServiceUniqueValidator implements FieldUniqueValidator {

    @Autowired
    UserService service;

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
            service.getByEmail(value.toString());
            return true;
        } catch (NotFoundException notFoundException) {
            return false;
        }
    }
}
