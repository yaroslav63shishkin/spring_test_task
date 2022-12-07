package com.example.spring_test_task.controller;

import com.example.spring_test_task.dto.error.ErrorResultDto;
import com.example.spring_test_task.exception.NoElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ResponseControllerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResultDto> handleValidationError(MethodArgumentNotValidException ex) {
        List<FieldError> constraintViolations = ex.getBindingResult().getFieldErrors();
        String debugMessage;
        if (constraintViolations.isEmpty()) {
            debugMessage = ex.getMessage();
        } else {
            debugMessage = constraintViolations.stream()
                    .map(x -> x.getField() + " " + x.getDefaultMessage() + " value was " + x.getRejectedValue())
                    .collect(Collectors.joining(" and "));
        }
        ErrorResultDto responseDto = ErrorResultDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .debugInfo(debugMessage)
                .build();
        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }

    @ExceptionHandler({NoElementException.class})
    public ResponseEntity<ErrorResultDto> handleMigrationError(NoElementException ex) {
        log.error(ex.getMessage(), ex);
        ErrorResultDto responseDto = ErrorResultDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                .debugInfo(ex.getMessage())
                .build();
        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResultDto> handleExceptionError(Exception e) {
        log.error(e.getMessage(), e);
        ErrorResultDto responseDto = ErrorResultDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .debugInfo(e.getMessage())
                .build();
        return ResponseEntity
                .status(responseDto.getStatus())
                .body(responseDto);
    }
}
