package com.example.zavrsnirad.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(code = HttpStatus.NOT_FOUND)
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
