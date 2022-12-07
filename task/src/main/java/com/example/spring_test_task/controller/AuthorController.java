package com.example.spring_test_task.controller;

import com.example.spring_test_task.dto.AuthorAllDto;
import com.example.spring_test_task.dto.AuthorDto;
import com.example.spring_test_task.dto.MarkerValidation;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.model.Author;
import com.example.spring_test_task.service.api.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("spring_test_task/${url.api.version}/authors")
public class AuthorController {

    private final AuthorService service;

    @PostMapping("/create")
    @Validated(MarkerValidation.OnCreate.class)
    public ResponseEntity<Author> createAuthor(@RequestBody @Valid AuthorDto author) {

        return new ResponseEntity<>(service.createAuthor(author), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<Page<AuthorDto>> getAllAndSort(@RequestBody @Valid PageRequestDto pageRequest) {

        return new ResponseEntity<>(service.getAuthors(pageRequest), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AuthorAllDto> getAuthorById(@PathVariable("id") @Positive Long id) {

        return new ResponseEntity<>(service.getAuthorById(id), HttpStatus.OK);
    }

    @PutMapping("/rewrite")
    @Validated(MarkerValidation.OnRewrite.class)
    public ResponseEntity<?> updateAuthor(@RequestBody @Valid AuthorDto authorDto) {

        service.rewriteAuthor(authorDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update")
    @Validated(MarkerValidation.OnUpdate.class)
    public ResponseEntity<?> updateFields(@RequestBody @Valid AuthorDto authorDto) {

        service.updateAuthor(authorDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAuthorById(@PathVariable("id") @Positive Long id) {

        service.removeAuthorById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
