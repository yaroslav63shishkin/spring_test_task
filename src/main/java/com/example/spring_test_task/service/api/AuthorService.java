package com.example.spring_test_task.service.api;

import com.example.spring_test_task.dto.AuthorAllDto;
import com.example.spring_test_task.dto.AuthorDto;
import com.example.spring_test_task.model.Author;
import com.example.spring_test_task.util.InputDataFormatUtil;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public interface AuthorService {

    Author createAuthor(@Valid AuthorDto authorDto);

    Page<AuthorDto> getAuthors(@PositiveOrZero Integer page, @Positive Integer size,
                               @Pattern(regexp = InputDataFormatUtil.NOT_BLANK) String sortField);

    AuthorAllDto getAuthorById(@Positive Long id);

    Author rewriteAuthor(@Valid AuthorDto authorDto);

    Author updateAuthor(@Valid AuthorDto authorDto);

    void removeAuthorById(@Positive Long id);
}
