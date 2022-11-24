package com.example.spring_test_task.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import static com.example.spring_test_task.util.InputDataFormatUtil.ISBN_FORMAT;

@Data
public class BookDto {

    @Positive
    @NotNull
    Long id;

    @NotNull
    @Pattern(regexp = ISBN_FORMAT, message = "Invalid isbn format (format: XXX-X-XXXXX-XXX-X) ")
    private String isbn;

    @NotNull
    private AuthorDto author;

    @NotNull
    @Valid
    private GenreDto genre;
}
