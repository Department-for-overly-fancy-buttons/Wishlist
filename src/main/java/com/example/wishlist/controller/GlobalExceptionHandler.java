package com.example.wishlist.controller;

import com.example.wishlist.exceptions.AccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountNotFoundException.class)
    public String handleNotFound(AccountNotFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", "Not Found");
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }
}
