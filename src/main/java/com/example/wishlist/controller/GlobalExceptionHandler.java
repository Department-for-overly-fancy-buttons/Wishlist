package com.example.wishlist.controller;

import com.example.wishlist.exceptions.*;
import com.example.wishlist.exceptions.DatabaseOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public String handleAccountNotFound(AccountNotFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", "Not Found");
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public String handleDuplicateAccount(DuplicateAccountException ex, Model model) {
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("error", "Duplicate Entry");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("redirectionUrl", "/");
        return "error/error";
    }

    @ExceptionHandler({ DatabaseOperationException.class})
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("error", "Internal Server Error");
        model.addAttribute("message", "Something went wrong. Please try again later.");
        return "error/500";
    }

    @ExceptionHandler({DuplicateWishlistException.class})
    public String handleDuplicateWishlist(Exception ex, Model model){
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("error", "Duplicate Entry");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("redirectionUrl", "/wishes/my_wishlists");
        return "error/error";
    }

    @ExceptionHandler({DuplicateWishException.class})
    public String handleDuplicateWish(Exception ex, Model model){
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("error", "Duplicate Entry");
        model.addAttribute("message", ex.getMessage());

        return "error/error";
    }

    @ExceptionHandler(WishNotFoundException.class)
    public String handleWishNotFound(WishNotFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", "Not Found");
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(WishlistNotFoundException.class)
    public String handleWishlistNotFound(WishlistNotFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", "Not Found");
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }

}
