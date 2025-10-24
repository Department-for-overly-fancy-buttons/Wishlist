package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishes")
public class WishlistController
{
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService)
    {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/add")
    public String showAddWishForm(Model model)
    {
        model.addAttribute("wish", new Wish(null, null, null));
        return "add-wish-form";
    }

    @PostMapping("/save")
    public String addWish(@ModelAttribute Wish wish)
    {
        Wish resultingWish = wishlistService.addWish(wish);

        if (resultingWish != null)
        {
            return "redirect:/wishes";
        }

        return "redirect:/";
    }

    @GetMapping
    public String getAllWishes(Model model)
    {
        model.addAttribute("wishes", wishlistService.getAllWishes());
        return "wish-list";
    }
}

