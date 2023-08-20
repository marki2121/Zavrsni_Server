package com.example.zavrsnirad.config;

import com.example.zavrsnirad.entity.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CostumeException {
    @ExceptionHandler(CostumeErrorException.class)
    public final ResponseEntity<ErrorMessage> CostumeErrorException(CostumeErrorException ex) {
        ErrorMessage error = new ErrorMessage(ex.getMessage(), ex.getStatus());
        return new ResponseEntity<ErrorMessage>(error, ex.getStatus());
    }
}
