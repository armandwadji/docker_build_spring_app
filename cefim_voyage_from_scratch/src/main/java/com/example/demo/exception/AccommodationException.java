package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class AccommodationException extends GlobalException{

    public AccommodationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
