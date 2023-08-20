package com.example.zavrsnirad.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorMessage {
    HttpStatus httpStatus;
    String message;

    public ErrorMessage(String message, HttpStatus status) {
        this.httpStatus = status;
        this.message = message;
    }
}
