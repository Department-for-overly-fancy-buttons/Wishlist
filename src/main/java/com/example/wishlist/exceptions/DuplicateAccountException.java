package com.example.wishlist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)

public class DuplicateAccountException extends RuntimeException {
    public DuplicateAccountException (String message) {
        super(message);
    }
}
