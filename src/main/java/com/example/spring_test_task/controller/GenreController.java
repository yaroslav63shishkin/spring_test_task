package com.example.spring_test_task.controller;

import com.example.spring_test_task.dto.GenreDto;
import com.example.spring_test_task.dto.MarkerValidation;
import com.example.spring_test_task.model.Genre;
import com.example.spring_test_task.service.api.GenreService;
import com.example.spring_test_task.util.InputDataFormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@RestController
@Validated
@Transactional
@RequestMapping("spring_test_task/${url.api.version}/genres")
public class GenreController {

    private GenreService service;

    @Autowired
    public void setService(GenreService service) {

        this.service = service;
    }

    @PostMapping("/create")
    @Validated(MarkerValidation.OnCreate.class)
    public ResponseEntity<Genre> createBook(@RequestBody @Valid GenreDto genreDto) {

        return new ResponseEntity<>(service.createGenre(genreDto), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<Page<GenreDto>> getAllGenresAndSort(
            @RequestParam(value = "page", defaultValue = "0") @PositiveOrZero Integer page,
            @RequestParam(value = "size", defaultValue = "15") @Positive Integer size,
            @RequestParam(value = "sort", defaultValue = "id")
            @Pattern(regexp = InputDataFormatUtil.NOT_BLANK) String sortField
    ) {
        return new ResponseEntity<>(service.getGenres(page, size, sortField), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable("id") @Positive Long id) {

        return new ResponseEntity<>(service.getGenreById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    @Validated(MarkerValidation.OnRewrite.class)
    public ResponseEntity<?> updateGenre(@RequestBody @Valid GenreDto genreDto) {

        service.rewriteGenre(genreDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update/fields")
    @Validated(MarkerValidation.OnUpdate.class)
    public ResponseEntity<?> updateFieldsGenre(@RequestBody @Valid GenreDto genreDto) {

        service.updateGenre(genreDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGenreById(@PathVariable("id") @Positive Long id) {

        service.removeGenreById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
