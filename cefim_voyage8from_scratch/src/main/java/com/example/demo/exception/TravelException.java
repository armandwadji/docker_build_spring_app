package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class TravelException extends GlobalException{
    public TravelException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
