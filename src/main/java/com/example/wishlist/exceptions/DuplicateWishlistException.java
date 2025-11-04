package com.example.wishlist.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateWishlistException extends RuntimeException {
    public DuplicateWishlistException(String message) {
        super(message);
    }
}
