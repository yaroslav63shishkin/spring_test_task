package com.example.spring_test_task.service;

import com.example.spring_test_task.dto.BookDto;
import com.example.spring_test_task.dto.BookSlimDto;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.exception.NoElementException;
import com.example.spring_test_task.mapper.BookMapper;
import com.example.spring_test_task.model.Book;
import com.example.spring_test_task.repository.BookRepository;
import com.example.spring_test_task.service.api.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static com.example.spring_test_task.util.CreatorCopiesUtil.copyProperties;

@Slf4j
@RequiredArgsConstructor
@Service
@Validated

public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final BookMapper mapper;

    @Override
    public Book createBook(@Valid BookSlimDto bookDto) {

        log.info("Input object for creation {}", bookDto);

        return repository.save(mapper.toBook(bookDto));
    }

    @Override
    public Page<BookSlimDto> getBooks(@Valid PageRequestDto pageRequest) {

        log.info("Input parameters for get page: pageNumber={}, size={}, sort={}",
                pageRequest.getPage(), pageRequest.getSize(), pageRequest.getSortField());

        return repository.findAll(
                        PageRequest.of(pageRequest.getPage(),
                                pageRequest.getSize(),
                                Sort.by(Sort.Direction.ASC, pageRequest.getSortField())))
                .map(mapper::toBookSlimDto);
    }

    @Override
    @Transactional
    public BookDto getBookById(@Positive Long id) {

        log.info("Input parameter fot get by ID: id={}", id);

        return mapper.toBookDto(
                       repository.findById(id)
                                 .orElseThrow(() ->
                                         new NoElementException("Book with ID " + id + " does not exist")));
    }

    @Override
    public Book rewriteBook(@Valid BookSlimDto bookDto) {

        log.info("Input object for rewrite {}", bookDto);

        Book bookOld = repository.findById(
                               bookDto.getId())
                                       .orElseThrow(() ->
                                               new NoElementException("Book with ID " + bookDto.getId() +
                                                                      " does not exist"));

        copyProperties(mapper.toBook(bookDto), bookOld);

        return repository.save(bookOld);
    }

    @Override
    public Book updateBook(@Valid BookSlimDto bookDto) {

        log.info("Input object for update {}", bookDto);

        Book bookOld = repository.findById(
                               bookDto.getId())
                                       .orElseThrow(() ->
                                               new NoElementException("Book with ID " + bookDto.getId() +
                                                                      " does not exist"));

        copyProperties(mapper.toBook(bookDto), bookOld);

        return repository.save(bookOld);
    }

    @Override
    @Transactional
    public void removeBookById(@Positive Long id) {

        log.info("Input parameter fot remove by ID: id={}", id);

        repository.findById(id)
                .orElseThrow(() ->
                        new NoElementException("Book with ID " + id + " does not exist"));

        repository.deleteOrphan(id);
        repository.deleteById(id);
    }
}
