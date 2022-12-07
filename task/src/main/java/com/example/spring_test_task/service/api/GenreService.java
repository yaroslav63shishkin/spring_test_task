package com.example.spring_test_task.service.api;

import com.example.spring_test_task.dto.GenreDto;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.model.Genre;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

public interface GenreService {

    Genre createGenre(@Valid GenreDto genreDto);

    Page<GenreDto> getGenres(@Valid PageRequestDto pageRequest);

    GenreDto getGenreById(@Positive Long id);

    Genre rewriteGenre(@Valid GenreDto genreDto);

    Genre updateGenre(@Valid GenreDto genreDto);

    void removeGenreById(@Positive Long id);
}
