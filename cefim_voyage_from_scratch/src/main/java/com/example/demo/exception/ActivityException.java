package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ActivityException extends GlobalException {

    public ActivityException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
