package com.example.wishlist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateWishException extends RuntimeException{
    public DuplicateWishException(String message) {
        super(message);
    }
}
