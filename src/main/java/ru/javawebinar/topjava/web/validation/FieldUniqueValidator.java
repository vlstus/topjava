package ru.javawebinar.topjava.web.validation;

public interface FieldUniqueValidator {
    boolean fieldValueExists(Object value, String fieldName);
}
