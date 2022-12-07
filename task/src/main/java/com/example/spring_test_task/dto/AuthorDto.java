package com.example.spring_test_task.dto;

import com.example.spring_test_task.util.annotation.NotEmptyString;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class AuthorDto {

    @Positive(groups = {MarkerValidation.OnRewrite.class, MarkerValidation.OnUpdate.class})
    @NotNull(groups = {MarkerValidation.OnRewrite.class, MarkerValidation.OnUpdate.class})
    @Null(groups = MarkerValidation.OnCreate.class)
    private Long id;

    @NotBlank(groups = MarkerValidation.OnRewrite.class)
    private String name;

    @NotBlank(groups = MarkerValidation.OnRewrite.class)
    private String surname;

    @NotBlank(groups = MarkerValidation.OnRewrite.class)
    @NotEmptyString(message = "Middle name consist of spaces or be empty ")
    private String middleName;

    @NotNull(groups = MarkerValidation.OnRewrite.class)
    @PastOrPresent
    private LocalDate dateOfBirth;
}
