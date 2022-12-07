package com.example.spring_test_task.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

@Data
public class GenreDto {

    @Positive(groups = {MarkerValidation.OnRewrite.class, MarkerValidation.OnUpdate.class})
    @NotNull(groups = {MarkerValidation.OnRewrite.class, MarkerValidation.OnUpdate.class})
    @Null(groups = MarkerValidation.OnCreate.class)
    private Long id;

    @NotBlank(groups = {MarkerValidation.OnRewrite.class, MarkerValidation.OnCreate.class})
    private String description;
}
