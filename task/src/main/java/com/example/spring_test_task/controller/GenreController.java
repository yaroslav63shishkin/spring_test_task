package com.example.spring_test_task.controller;

import com.example.spring_test_task.dto.GenreDto;
import com.example.spring_test_task.dto.MarkerValidation;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.model.Genre;
import com.example.spring_test_task.service.api.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("spring_test_task/${url.api.version}/genres")
public class GenreController {

    private final GenreService service;

    @PostMapping("/create")
    @Validated(MarkerValidation.OnCreate.class)
    public ResponseEntity<Genre> createBook(@RequestBody @Valid GenreDto genreDto) {

        return new ResponseEntity<>(service.createGenre(genreDto), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<Page<GenreDto>> getAllGenresAndSort(@RequestBody @Valid PageRequestDto pageRequest) {

        return new ResponseEntity<>(service.getGenres(pageRequest), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable("id") @Positive Long id) {

        return new ResponseEntity<>(service.getGenreById(id), HttpStatus.OK);
    }

    @PutMapping("/rewrite")
    @Validated(MarkerValidation.OnRewrite.class)
    public ResponseEntity<?> updateGenre(@RequestBody @Valid GenreDto genreDto) {

        service.rewriteGenre(genreDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update")
    @Validated(MarkerValidation.OnUpdate.class)
    public ResponseEntity<?> updateFields(@RequestBody @Valid GenreDto genreDto) {

        service.updateGenre(genreDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGenreById(@PathVariable("id") @Positive Long id) {

        service.removeGenreById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
