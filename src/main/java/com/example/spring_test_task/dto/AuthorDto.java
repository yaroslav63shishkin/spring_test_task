package com.example.spring_test_task.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

import static com.example.spring_test_task.util.InputDataFormatUtil.NOT_BLANK;

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
    @Pattern(regexp = NOT_BLANK, message = "Middle name cannot consist of spaces ")
    private String middleName;

    @NotNull(groups = MarkerValidation.OnRewrite.class)
    @PastOrPresent
    private LocalDate dateOfBirth;
}
