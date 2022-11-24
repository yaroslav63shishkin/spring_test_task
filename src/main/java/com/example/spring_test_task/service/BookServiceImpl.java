package com.example.spring_test_task.service;

import com.example.spring_test_task.dto.AuthorDto;
import com.example.spring_test_task.dto.BookDto;
import com.example.spring_test_task.dto.BookSlimDto;
import com.example.spring_test_task.exception.NoElementException;
import com.example.spring_test_task.exception.NonExistentPageException;
import com.example.spring_test_task.mapper.BookMapper;
import com.example.spring_test_task.model.Book;
import com.example.spring_test_task.repository.BookRepository;
import com.example.spring_test_task.service.api.BookService;
import com.example.spring_test_task.util.InputDataFormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static com.example.spring_test_task.util.CreatorCopiesUtil.copyProperties;

@Slf4j
@Service
@Validated
@Transactional
public class BookServiceImpl implements BookService {

    BookRepository repository;

    BookMapper mapper;

    @Autowired
    public void setRepository(BookRepository repository) {

        this.repository = repository;
    }

    @Autowired
    public void setMapper(BookMapper mapper) {

        this.mapper = mapper;
    }

    @Override
    public Book createBook(@Valid BookSlimDto bookSlimDto) {

        log.info("Input object for creation {}", bookSlimDto);

        return repository.save(mapper.toBook(bookSlimDto));
    }

    @Override
    public Page<BookSlimDto> getBooks(@PositiveOrZero Integer pageNumber, @Positive Integer size,
                                  @Pattern(regexp = InputDataFormatUtil.NOT_BLANK) String sortField) {

        log.info("Input parameters for get page: pageNumber={}, size={}, sort={}", pageNumber, size, sortField);

        Page<BookSlimDto> pageBook = repository.findAll(
                        PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, sortField)))
                .map(mapper::toBookSlimDto);

        if (pageNumber > pageBook.getTotalPages()) {
            throw new NonExistentPageException("Page "+ pageNumber +"does not exist");
        }

        return pageBook;
    }

    @Override
    public BookDto getBookById(@Positive Long id) {

        log.info("Input parameter fot get by ID: id={}", id);

        return mapper.toBookDto(repository.findById(id)
                .orElseThrow(() -> new NoElementException("Book with ID " + id + " does not exist")));
    }

    @Override
    public Book rewriteBook(@Valid BookDto bookDto) {

        log.info("Input object for rewrite {}", bookDto);

        Book bookOld = repository.findById(bookDto.getId()).orElseThrow(() ->
                new NoElementException("Book with ID " + bookDto.getId() + " does not exist"));

        return repository.save(bookOld);
    }

    @Override
    public Book updateBook(@Valid BookSlimDto bookSlimDto) {

        log.info("Input object for update {}", bookSlimDto);

        Book bookOld = repository.findById(bookSlimDto.getId()).orElseThrow(() ->
                new NoElementException("Book with ID " + bookSlimDto.getId() + " does not exist"));

        copyProperties(mapper.toBook(bookSlimDto), bookOld);

        return repository.save(bookOld);
    }

    @Override
    public void removeBookById(@Positive Long id) {

        log.info("Input parameter fot remove by ID: id={}", id);

        repository.deleteById(id);
    }
}
