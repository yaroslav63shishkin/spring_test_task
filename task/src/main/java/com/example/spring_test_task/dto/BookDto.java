package com.example.spring_test_task.dto;

import lombok.Data;

import java.util.Set;

@Data
public class BookDto {

    Long id;

    private String isbn;

    private Set<AuthorDto> authors;

    private Set<GenreDto> genres;
}
