package com.caixeta.gildedrose.exception;

import com.caixeta.gildedrose.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice(basePackages = "com.caixeta.gildedrose")
public class GlobalExceptionHandler {

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<MessageResponse> itemNotFound(RuntimeException e) {
        return new ResponseEntity<>(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Item not found or not available!"), HttpStatus.BAD_REQUEST);
    }

}
