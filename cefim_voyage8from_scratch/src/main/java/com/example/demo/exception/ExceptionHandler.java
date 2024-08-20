package com.example.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {

@org.springframework.web.bind.annotation.ExceptionHandler(GlobalException.class)
public ResponseEntity<ExceptionMessage> globalException(HttpServletRequest request, GlobalException exception) {

    return ResponseEntity.status(exception.getHttpStatus())
            .body(ExceptionMessage.builder()
                    .date(LocalDateTime.now())
                    .request(request.getRequestURI())
                    .message(exception.getMessage())
                    .build());
}
}