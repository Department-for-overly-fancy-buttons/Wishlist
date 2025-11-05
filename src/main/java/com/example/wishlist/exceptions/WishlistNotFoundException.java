package com.example.wishlist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WishlistNotFoundException extends RuntimeException {
    public WishlistNotFoundException(String message) {
        super(message);
    }
}
