package com.example.spring_test_task.service;

import com.example.spring_test_task.dto.GenreDto;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.exception.NoElementException;
import com.example.spring_test_task.mapper.GenreMapperImpl;
import com.example.spring_test_task.model.Genre;
import com.example.spring_test_task.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class GenreServiceImplModuleTest {

    @Mock
    private GenreRepository repository;

    @Mock
    private GenreMapperImpl mapper;

    @InjectMocks
    private GenreServiceImpl service;

    @Test
    void createGenre() {
        when(repository.save(any())).thenReturn(getGenre());
        service.createGenre(getGenreDto());
        verify(repository,times(1)).save(any());
    }

    @Test
    void getGenres() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "id"));
        List<Genre> authors = new ArrayList<>();
        authors.add(Genre.builder().id(1L).build());
        authors.add(Genre.builder().id(2L).build());
        authors.add(Genre.builder().id(3L).build());

        long totalAuthors = 3L;

        Page<Genre> pageResult = new PageImpl<>(authors, pageable, totalAuthors);

        when(repository.findAll(any(Pageable.class))).thenReturn(pageResult);

        Page<GenreDto> page = service.getGenres(new PageRequestDto());

        assertEquals(totalAuthors, page.getTotalElements());
        assertEquals(pageResult.getTotalPages(), page.getTotalPages());
    }

    @Test
    void getGenreByIdTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.getGenreById(getGenreDto().getId()));
    }

    @Test
    void rewriteGenreTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.getGenreById(getGenreDto().getId()));
    }

    @Test
    void updateGenreTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.getGenreById(getGenreDto().getId()));
    }

    @Test
    void removeGenreByIdTrowNoElementException() {

        assertThrows(NoElementException.class, () -> service.getGenreById(getGenreDto().getId()));
    }

    private Genre getGenre() {
        return Genre.builder()
                .id(1L)
                .description("Драма")
                .build();
    }

    private GenreDto getGenreDto() {

        GenreDto genreDto = new GenreDto();
        genreDto.setId(1L);
        genreDto.setDescription("Драма");

        return genreDto;
    }
}