package com.example.spring_test_task.service;

import com.example.spring_test_task.dto.BookSlimDto;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.exception.NoElementException;
import com.example.spring_test_task.mapper.BookMapperImpl;
import com.example.spring_test_task.model.Book;
import com.example.spring_test_task.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceImplModuleTest {

    @Mock
    private BookRepository repository;

    @Mock
    private BookMapperImpl mapper;

    @InjectMocks
    private BookServiceImpl service;

    @Test
    void createBook() {
        when(repository.save(any())).thenReturn(getBook());
        service.createBook(getBookSlimDto());
        verify(repository,times(1)).save(any());
    }

    @Test
    void getBooks() {

        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "id"));
        List<Book> authors = new ArrayList<>();
        authors.add(Book.builder().id(1L).build());
        authors.add(Book.builder().id(2L).build());
        authors.add(Book.builder().id(3L).build());

        long totalAuthors = 3L;

        Page<Book> pageResult = new PageImpl<>(authors, pageable, totalAuthors);

        when(repository.findAll(any(Pageable.class))).thenReturn(pageResult);

        Page<BookSlimDto> page = service.getBooks(new PageRequestDto());

        assertEquals(totalAuthors, page.getTotalElements());
        assertEquals(pageResult.getTotalPages(), page.getTotalPages());
    }

    @Test
    void getBookByIdTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.getBookById(getBookSlimDto().getId()));
    }

    @Test
    void rewriteBookTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.getBookById(getBookSlimDto().getId()));
    }

    @Test
    void updateBookTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.getBookById(getBookSlimDto().getId()));
    }

    @Test
    void removeBookByIdTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.getBookById(getBookSlimDto().getId()));
    }

    private Book getBook() {
        return Book.builder()
                .id(1L)
                .isbn("9781556156786")
                .build();
    }

    private BookSlimDto getBookSlimDto() {

        BookSlimDto bookSlimDto = new BookSlimDto();
        bookSlimDto.setId(1L);
        bookSlimDto.setIsbn("9781556156786");

        return bookSlimDto;
    }
}