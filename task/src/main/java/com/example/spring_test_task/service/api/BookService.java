package com.example.spring_test_task.service.api;

import com.example.spring_test_task.dto.BookDto;
import com.example.spring_test_task.dto.BookSlimDto;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.model.Book;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

public interface BookService {

    Book createBook(@Valid BookSlimDto bookDto);

    Page<BookSlimDto> getBooks(@Valid PageRequestDto pageRequest);

    BookDto getBookById(@Positive Long id);

    Book rewriteBook(@Valid BookSlimDto bookDto);

    Book updateBook(@Valid BookSlimDto bookDto);

    void removeBookById(@Positive Long id);
}
