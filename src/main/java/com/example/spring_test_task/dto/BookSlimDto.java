package com.example.spring_test_task.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import static com.example.spring_test_task.util.InputDataFormatUtil.ISBN_FORMAT;

@Data
public class BookSlimDto {

    @Positive(groups = {MarkerValidation.OnRewrite.class, MarkerValidation.OnUpdate.class})
    @NotNull(groups = {MarkerValidation.OnRewrite.class, MarkerValidation.OnUpdate.class})
    @Null(groups = MarkerValidation.OnCreate.class)
    private Long id;

    @NotNull(groups = {MarkerValidation.OnRewrite.class, MarkerValidation.OnCreate.class})
    @Pattern(regexp = ISBN_FORMAT, message = "Invalid isbn format (format: XXX-X-XXXXX-XXX-X) ")
    private String isbn;
}
