package com.example.spring_test_task.dto;

import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

@Data
public class BookSlimDto {

    @Positive(groups = {MarkerValidation.OnRewrite.class, MarkerValidation.OnUpdate.class})
    @NotNull(groups = {MarkerValidation.OnRewrite.class, MarkerValidation.OnUpdate.class})
    @Null(groups = MarkerValidation.OnCreate.class)
    private Long id;

    @NotNull(groups = {MarkerValidation.OnRewrite.class, MarkerValidation.OnCreate.class})
    @ISBN
    private String isbn;
}
