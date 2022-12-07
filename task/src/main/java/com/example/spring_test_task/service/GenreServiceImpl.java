package com.example.spring_test_task.service;

import com.example.spring_test_task.dto.GenreDto;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.exception.NoElementException;
import com.example.spring_test_task.mapper.GenreMapper;
import com.example.spring_test_task.model.Genre;
import com.example.spring_test_task.repository.GenreRepository;
import com.example.spring_test_task.service.api.GenreService;
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
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    private final GenreMapper mapper;

    @Override
    public Genre createGenre(@Valid GenreDto genreDto) {

        log.info("Input object for creation {}", genreDto);

        return repository.save(mapper.toGenre(genreDto));
    }

    @Override
    public Page<GenreDto> getGenres(@Valid PageRequestDto pageRequest) {

        log.info("Input parameters for get page: pageNumber={}, size={}, sort={}",
                pageRequest.getPage(), pageRequest.getSize(), pageRequest.getSortField());

        return repository.findAll(
                        PageRequest.of(pageRequest.getPage(),
                                pageRequest.getSize(),
                                Sort.by(Sort.Direction.ASC, pageRequest.getSortField())))
                .map(mapper::toGenreDto);
    }

    @Override
    public GenreDto getGenreById(@Positive Long id) {

        log.info("Input parameter fot get by ID: id={}", id);

        return mapper.toGenreDto(
                       repository.findById(id)
                               .orElseThrow(() ->
                                       new NoElementException("Genre with ID " + id + " does not exist")));
    }

    @Override
    public Genre rewriteGenre(@Valid GenreDto genreDto) {

        log.info("Input object for rewrite {}", genreDto);

        Genre genreOld = repository.findById(
                                 genreDto.getId())
                                         .orElseThrow(() ->
                                                 new NoElementException("Genre with ID " + genreDto.getId() +
                                                                        " does not exist"));

        copyProperties(mapper.toGenre(genreDto), genreOld);

        return repository.save(genreOld);
    }

    @Override
    public Genre updateGenre(@Valid GenreDto genreDto) {

        log.info("Input object for update {}", genreDto);

        Genre genreOld = repository.findById(
                                 genreDto.getId())
                                         .orElseThrow(() ->
                                                 new NoElementException("Genre with ID " + genreDto.getId() +
                                                                        " does not exist"));

        copyProperties(mapper.toGenre(genreDto), genreOld);

        return repository.save(genreOld);
    }

    @Override
    @Transactional
    public void removeGenreById(@Positive Long id) {

        log.info("Input parameter fot remove by ID: id={}", id);

        repository.findById(id)
                .orElseThrow(() ->
                        new NoElementException("Genre with ID " + id + " does not exist"));

        repository.deleteOrphan(id);
        repository.deleteById(id);
    }
}
