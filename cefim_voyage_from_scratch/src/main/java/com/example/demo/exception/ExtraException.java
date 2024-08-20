package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ExtraException extends GlobalException{

    public ExtraException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
