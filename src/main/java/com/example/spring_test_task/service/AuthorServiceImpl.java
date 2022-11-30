package com.example.spring_test_task.service;

import com.example.spring_test_task.dto.AuthorAllDto;
import com.example.spring_test_task.dto.AuthorDto;
import com.example.spring_test_task.exception.NoElementException;
import com.example.spring_test_task.exception.NonExistentPageException;
import com.example.spring_test_task.mapper.AuthorMapper;
import com.example.spring_test_task.model.Author;
import com.example.spring_test_task.repository.AuthorRepository;
import com.example.spring_test_task.service.api.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository repository;

    AuthorMapper mapper;

    @Autowired
    public void setRepository(AuthorRepository repository) {

        this.repository = repository;
    }

    @Autowired
    public void setMapper(AuthorMapper mapper) {

        this.mapper = mapper;
    }

    @Override
    public Author createAuthor(@Valid AuthorDto authorDto) {

        log.info("Input object for creation {}", authorDto);

        return repository.save(mapper.toAuthor(authorDto));
    }

    @Override
    public Page<AuthorDto> getAuthors(@PositiveOrZero Integer pageNumber, @Positive Integer size,
                                      @Pattern(regexp = InputDataFormatUtil.NOT_BLANK) String sortField) {

        log.info("Input parameters for get page: pageNumber={}, size={}, sort={}", pageNumber, size, sortField);

        Page<AuthorDto> pageAuthor = repository.findAll(
                        PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, sortField)))
                .map(mapper::toAuthorDto);

        if (pageNumber > pageAuthor.getTotalPages()) {
            throw new NonExistentPageException("Page " + pageNumber + " does not exist"); //fixme: лучше возвращать пустой результат
        }

        return pageAuthor;
    }

    @Override
    public AuthorAllDto getAuthorById(@Positive Long id) {

        log.info("Input parameter fot get by ID: id={}", id);

        return mapper.toAuthorAllDto(repository.findById(id)
                .orElseThrow(() -> new NoElementException("Author with ID " + id + " does not exist")));
    }

    @Override
    public Author rewriteAuthor(@Valid AuthorDto authorDto) {

        log.info("Input object for rewrite {}", authorDto);

        repository.findById(authorDto.getId()).orElseThrow(() ->
                        new NoElementException("Author with ID " + authorDto.getId() + " does not exist"));

        return repository.save(mapper.toAuthor(authorDto)); // fixme: сохраняет нового а не изменяет старого?
    }

    @Override
    public Author updateAuthor(@Valid AuthorDto authorDto) {

        log.info("Input object for update {}", authorDto);

        Author authorOld = repository.findById(authorDto.getId()).orElseThrow(() ->
                new NoElementException("Author with ID " + authorDto.getId() + " does not exist"));

        copyProperties(mapper.toAuthor(authorDto), authorOld);

        return repository.save(authorOld);
    }

    @Override
    public void removeAuthorById(@Positive Long id) {

        log.info("Input parameter fot remove by ID: id={}", id);

        repository.deleteById(id);
    }
}
