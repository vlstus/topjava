package ru.javawebinar.topjava.web.validation.constraint;

import ru.javawebinar.topjava.web.validation.FieldUniqueValidator;
import ru.javawebinar.topjava.web.validation.validator.UniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Documented
public @interface Unique {
    String message() default "{unique.value.violation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends FieldUniqueValidator> service();

    String serviceQualifier() default "";

    String fieldName();
}
