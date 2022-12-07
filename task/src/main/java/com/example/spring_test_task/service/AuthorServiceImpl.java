package com.example.spring_test_task.service;

import com.example.spring_test_task.dto.AuthorAllDto;
import com.example.spring_test_task.dto.AuthorDto;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.exception.NoElementException;
import com.example.spring_test_task.mapper.AuthorMapper;
import com.example.spring_test_task.model.Author;
import com.example.spring_test_task.repository.AuthorRepository;
import com.example.spring_test_task.service.api.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    private final AuthorMapper mapper;

    @Override
    public Author createAuthor(@Valid AuthorDto authorDto) {

        log.info("Input object for creation {}", authorDto);

        return repository.save(mapper.toAuthor(authorDto));
    }

    @Override
    public Page<AuthorDto> getAuthors(@Valid PageRequestDto pageRequest) {

        log.info("Input parameters for get page: pageNumber={}, size={}, sort={}",
                pageRequest.getPage(), pageRequest.getSize(), pageRequest.getSortField());

        return repository.findAll(
                        PageRequest.of(pageRequest.getPage(),
                                pageRequest.getSize(),
                                Sort.by(Sort.Direction.ASC, pageRequest.getSortField())))
                .map(mapper::toAuthorDto);
    }

    @Override
    @Transactional
    public AuthorAllDto getAuthorById(@Positive Long id) {

        log.info("Input parameter fot get by ID: id={}", id);

        return mapper.toAuthorAllDto(
                        repository.findById(id)
                                  .orElseThrow(() ->
                                          new NoElementException("Author with ID " + id + " does not exist")));
    }

    @Override
    public Author rewriteAuthor(@Valid AuthorDto authorDto) {

        log.info("Input object for rewrite {}", authorDto);

        Author authorOld = repository.findById(
                                   authorDto.getId())
                                           .orElseThrow(() ->
                                                   new NoElementException("Author with ID " + authorDto.getId() +
                                                                          " does not exist"));

        copyProperties(mapper.toAuthor(authorDto), authorOld);

        return repository.save(authorOld);
    }

    @Override
    public Author updateAuthor(@Valid AuthorDto authorDto) {

        log.info("Input object for update {}", authorDto);

        Author authorOld = repository.findById(
                                   authorDto.getId())
                                           .orElseThrow(() ->
                                                   new NoElementException("Author with ID " + authorDto.getId() +
                                                                          " does not exist"));

        copyProperties(mapper.toAuthor(authorDto), authorOld);

        return repository.save(authorOld);
    }

    @Override
    public void removeAuthorById(@Positive Long id) {

        log.info("Input parameter fot remove by ID: id={}", id);

        repository.findById(id)
                .orElseThrow(() ->
                        new NoElementException("Author with ID " + id + " does not exist"));

        repository.deleteById(id);
    }
}
