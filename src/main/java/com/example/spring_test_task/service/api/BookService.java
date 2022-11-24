package com.example.spring_test_task.service.api;

import com.example.spring_test_task.dto.BookDto;
import com.example.spring_test_task.dto.BookSlimDto;
import com.example.spring_test_task.model.Book;
import com.example.spring_test_task.util.InputDataFormatUtil;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public interface BookService {

    Book createBook(@Valid BookSlimDto bookSlimDto);

    Page<BookSlimDto> getBooks(@PositiveOrZero Integer page, @Positive Integer size,
                               @Pattern(regexp = InputDataFormatUtil.NOT_BLANK) String sortField);

    BookDto getBookById(@Positive Long id);

    Book rewriteBook(@Valid BookDto bookDto);

    Book updateBook(@Valid BookSlimDto bookSlimDto);

    void removeBookById(@Positive Long id);
}
