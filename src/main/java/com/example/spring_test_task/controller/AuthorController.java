package com.example.spring_test_task.controller;

import com.example.spring_test_task.dto.AuthorAllDto;
import com.example.spring_test_task.dto.AuthorDto;
import com.example.spring_test_task.dto.MarkerValidation;
import com.example.spring_test_task.model.Author;
import com.example.spring_test_task.service.api.AuthorService;
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
@RequestMapping("spring_test_task/${url.api.version}/authors")
public class AuthorController {

    private AuthorService service;

    @Autowired
    public void setService(AuthorService service) {

        this.service = service;
    }

    @PostMapping("/create")
    @Validated(MarkerValidation.OnCreate.class)
    public ResponseEntity<Author> createAuthor(@RequestBody @Valid AuthorDto author) {

        return new ResponseEntity<>(service.createAuthor(author), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<Page<AuthorDto>> getAllAndSort(
            @RequestParam(value = "page", defaultValue = "0") @PositiveOrZero Integer page,
            @RequestParam(value = "size", defaultValue = "15") @Positive Integer size,
            @RequestParam(value = "sort", defaultValue = "id")
            @Pattern(regexp = InputDataFormatUtil.NOT_BLANK) String sortField
    ) {
        return new ResponseEntity<>(service.getAuthors(page, size, sortField), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AuthorAllDto> getAuthorById(@PathVariable("id") @Positive Long id) {

        return new ResponseEntity<>(service.getAuthorById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    @Validated(MarkerValidation.OnRewrite.class)
    public ResponseEntity<?> updateAuthor(@RequestBody @Valid AuthorDto authorDto) {

        service.rewriteAuthor(authorDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update/fields")
    @Validated(MarkerValidation.OnUpdate.class)
    public ResponseEntity<?> updateFieldsAuthor(@RequestBody @Valid AuthorDto authorDto) {

        service.updateAuthor(authorDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable("id") @Positive Long id) {

        service.removeAuthorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
