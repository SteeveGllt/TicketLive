package com.steeve.ticketlive.exception;

import com.steeve.ticketlive.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ConcertNotFoundException.class)
    public ResponseEntity<Object> handleConcertNotFoundException(ConcertNotFoundException cnfe){
        return ResponseEntity.status(cnfe.getError().getCode())
                .body(new ErrorResponse(HttpStatus.resolve(cnfe.getError().getCode()), cnfe.getError().getMessage(), List.of(cnfe.getMessage())));
    }
}
