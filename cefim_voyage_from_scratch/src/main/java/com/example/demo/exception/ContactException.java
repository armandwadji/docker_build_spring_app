package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ContactException extends GlobalException{

    public ContactException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
