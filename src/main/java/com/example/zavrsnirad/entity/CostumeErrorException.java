package com.example.zavrsnirad.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CostumeErrorException extends Exception {
    private HttpStatus status = null;
    private Object data = null;

    public CostumeErrorException() {
        super();
    }

    public CostumeErrorException(String message) {
        super(message);
    }

    public CostumeErrorException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public CostumeErrorException(String message, HttpStatus status, Object data) {
        super(message);
        this.status = status;
        this.data = data;
    }
}
