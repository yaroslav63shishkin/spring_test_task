package com.example.spring_test_task.exception;

public class NonExistentPageException extends RuntimeException {

    public NonExistentPageException(String message) {
        super(message);
    }
}
