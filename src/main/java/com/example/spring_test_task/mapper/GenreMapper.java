package com.example.spring_test_task.mapper;

import com.example.spring_test_task.dto.GenreDto;
import com.example.spring_test_task.model.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class GenreMapper {

    public abstract GenreDto toGenreDto(Genre genre);

    public abstract Genre toGenre(GenreDto genreDto);
}

