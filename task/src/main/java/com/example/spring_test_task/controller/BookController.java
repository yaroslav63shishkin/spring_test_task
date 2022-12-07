package com.example.spring_test_task.controller;

import com.example.spring_test_task.dto.BookDto;
import com.example.spring_test_task.dto.BookSlimDto;
import com.example.spring_test_task.dto.MarkerValidation;
import com.example.spring_test_task.dto.request.PageRequestDto;
import com.example.spring_test_task.model.Book;
import com.example.spring_test_task.service.api.BookService;
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
@RequestMapping("spring_test_task/${url.api.version}/books")
public class BookController {

    private final BookService service;

    @PostMapping("/create")
    @Validated(MarkerValidation.OnCreate.class)
    public ResponseEntity<Book> createBook(@RequestBody @Valid BookSlimDto bookSlimDto) {

        return new ResponseEntity<>(service.createBook(bookSlimDto), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<Page<BookSlimDto>> getAllBooksAndSort(@RequestBody @Valid PageRequestDto pageRequest) {

        return new ResponseEntity<>(service.getBooks(pageRequest), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookDto> getAuthorById(@PathVariable("id") @Positive Long id) {

        return new ResponseEntity<>(service.getBookById(id), HttpStatus.OK);
    }

    @PutMapping("/rewrite")
    @Validated(MarkerValidation.OnRewrite.class)
    public ResponseEntity<?> updateBook(@RequestBody @Valid BookSlimDto bookDto) {

        service.rewriteBook(bookDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update")
    @Validated(MarkerValidation.OnUpdate.class)
    public ResponseEntity<?> updateFields(@RequestBody @Valid BookSlimDto bookSlimDto) {

        service.updateBook(bookSlimDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable("id") @Positive Long id) {

        service.removeBookById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
