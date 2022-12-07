package com.example.spring_test_task.util.annotation;

import com.example.spring_test_task.util.NotEmptyStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyStringValidator.class)
@Documented
public @interface NotEmptyString {

    String message() default "String should not be empty or space ";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
