package ru.javawebinar.topjava.web.validation.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.javawebinar.topjava.web.validation.FieldUniqueValidator;
import ru.javawebinar.topjava.web.validation.constraint.Unique;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @Autowired
    private ApplicationContext applicationContext;

    private FieldUniqueValidator service;
    private String fieldName;

    @Override
    public void initialize(Unique unique) {
        Class<? extends FieldUniqueValidator> clazz = unique.service();
        this.fieldName = unique.fieldName();
        String serviceQualifier = unique.serviceQualifier();

        if (!serviceQualifier.equals("")) {
            this.service = this.applicationContext.getBean(serviceQualifier, clazz);
        } else {
            this.service = this.applicationContext.getBean(clazz);
        }
    }

    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        return !this.service.fieldValueExists(obj, this.fieldName);
    }
}
