package com.example.spring_test_task.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class InputDataFormatUtil {

    public static final Pattern NOT_BLANK = Pattern.compile("[А-Яа-яA-Za-z]+\\s?[А-Яа-яA-Za-z]*");
}
