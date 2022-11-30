package com.example.spring_test_task.service;

import com.example.spring_test_task.dto.AuthorDto;
import com.example.spring_test_task.dto.GenreDto;
import com.example.spring_test_task.exception.NoElementException;
import com.example.spring_test_task.exception.NonExistentPageException;
import com.example.spring_test_task.mapper.GenreMapper;
import com.example.spring_test_task.model.Genre;
import com.example.spring_test_task.repository.GenreRepository;
import com.example.spring_test_task.service.api.GenreService;
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
public class GenreServiceImpl implements GenreService {

    GenreRepository repository;

    GenreMapper mapper;

    @Autowired
    public void setRepository(GenreRepository repository) {

        this.repository = repository;
    }

    @Autowired
    public void setMapper(GenreMapper mapper) {

        this.mapper = mapper;
    }

    @Override
    public Genre createGenre(@Valid GenreDto genreDto) {

        log.info("Input object for creation {}", genreDto);

        return repository.save(mapper.toGenre(genreDto));
    }

    @Override
    public Page<GenreDto> getGenres(@PositiveOrZero Integer pageNumber, @Positive Integer size,
                                      @Pattern(regexp = InputDataFormatUtil.NOT_BLANK) String sortField) {

        log.info("Input parameters for get page: pageNumber={}, size={}, sort={}", pageNumber, size, sortField);

        Page<GenreDto> pageGenre = repository.findAll(
                        PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, sortField)))
                .map(mapper::toGenreDto);

        if (pageNumber > pageGenre.getTotalPages()) {
            throw new NonExistentPageException("Page "+ pageNumber +"does not exist");
        }

        return pageGenre;
    }

    @Override
    public GenreDto getGenreById(@Positive Long id) {

        log.info("Input parameter fot get by ID: id={}", id);

        // todo: вызовы лучше разделять, проще читать и определять проблемные места + анализ ide поможет избежать NPE
        return mapper.toGenreDto(repository.findById(id)
                .orElseThrow(() -> new NoElementException("Book with ID " + id + " does not exist")));
    }

    @Override
    public Genre rewriteGenre(@Valid GenreDto genreDto) {

        log.info("Input object for rewrite {}", genreDto);

        repository.findById(genreDto.getId()).orElseThrow(() ->
                new NoElementException("Book with ID " + genreDto.getId() + " does not exist"));

        return repository.save(mapper.toGenre(genreDto));
    }

    @Override
    public Genre updateGenre(@Valid GenreDto genreDto) {

        log.info("Input object for update {}", genreDto);

        Genre genreOld = repository.findById(genreDto.getId()).orElseThrow(() ->
                new NoElementException("Book with ID " + genreDto.getId() + " does not exist"));

        copyProperties(mapper.toGenre(genreDto), genreOld);

        return repository.save(genreOld);
    }

    @Override
    public void removeGenreById(@Positive Long id) {

        log.info("Input parameter fot remove by ID: id={}", id);

        repository.deleteById(id);
    }
}
