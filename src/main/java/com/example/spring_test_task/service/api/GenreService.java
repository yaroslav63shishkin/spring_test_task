package com.example.spring_test_task.service.api;

import com.example.spring_test_task.dto.GenreDto;
import com.example.spring_test_task.model.Genre;
import com.example.spring_test_task.util.InputDataFormatUtil;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public interface GenreService {

    Genre createGenre(@Valid GenreDto genreDto);

    Page<GenreDto> getGenres(@PositiveOrZero Integer page, @Positive Integer size,
                               @Pattern(regexp = InputDataFormatUtil.NOT_BLANK) String sortField);

    GenreDto getGenreById(@Positive Long id);

    Genre rewriteGenre(@Valid GenreDto genreDto);

    Genre updateGenre(@Valid GenreDto genreDto);

    void removeGenreById(@Positive Long id);
}
