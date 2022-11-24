package com.example.spring_test_task.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InputDataFormatUtil {

    public static final String NOT_BLANK = "[А-Яа-яA-Za-z]+\\s?[А-Яа-яA-Za-z]*";
    public static final String ISBN_FORMAT = "\\d{3}-\\d-\\d{5}-\\d{3}-\\d";
}
