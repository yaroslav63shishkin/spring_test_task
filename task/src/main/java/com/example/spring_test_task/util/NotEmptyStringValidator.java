package com.example.spring_test_task.util;

import com.example.spring_test_task.util.annotation.NotEmptyString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyStringValidator implements ConstraintValidator<NotEmptyString, String> {

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {

        return string.matches(InputDataFormatUtil.NOT_BLANK.pattern());
    }
}
