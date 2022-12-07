package com.example.spring_test_task.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class AuthorAllDto {

    private Long id;

    private String name;

    private String surname;

    private String middleName;

    private LocalDate dateOfBirth;

    private Set<BookSlimDto> books;
}
