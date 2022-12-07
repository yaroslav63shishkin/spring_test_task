package com.example.spring_test_task.service.api;

import com.example.spring_test_task.dto.AuthorAllDto;
import com.example.spring_test_task.dto.AuthorDto;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.model.Author;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

public interface AuthorService {

    Author createAuthor(@Valid AuthorDto authorDto);

    Page<AuthorDto> getAuthors(@Valid PageRequestDto pageRequest);

    AuthorAllDto getAuthorById(@Positive Long id);

    Author rewriteAuthor(@Valid AuthorDto authorDto);

    Author updateAuthor(@Valid AuthorDto authorDto);

    void removeAuthorById(@Positive Long id);
}
