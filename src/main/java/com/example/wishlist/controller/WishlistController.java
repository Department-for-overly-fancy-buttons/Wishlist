package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/wishes")
public class WishlistController
{
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService)
    {
        this.wishlistService = wishlistService;
    }

    @GetMapping()
    public String createWishlist(Model model){
        model.addAttribute("wishlist", new Wishlist());
        return "create-wishlist-form";
    }

    @PostMapping("/create/wishlist")
    public String createNewWishlist(){
        return "redirect:/";
    }

    @GetMapping("/add")
    public String showAddWishForm(Model model)
    {
        model.addAttribute("wish", new Wish());
        return "add-wish-form";
    }

    @PostMapping("/save")
    public String addWish(@ModelAttribute Wish wish)
    {
        Wish resultingWish = wishlistService.addWish(wish);
        return (resultingWish != null) ? "redirect:/wishes" : "redirect:/";
    }

    @GetMapping("/allwishes")
    public String getAllWishes(Model model)
    {
        model.addAttribute("wishes", wishlistService.getAllWishes());
        return "wish-list";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateWishForm(@PathVariable int id, Model model)
    {
        Wish wish = wishlistService.getWish(id);
        if (wish != null)
        {
            model.addAttribute("wish", wish);

            return "update-wish-form";
        }
        return "redirect:/";
    }

    @PostMapping("{id}/delete")
    public String deleteWish(@PathVariable int id, RedirectAttributes redirectAttributes)
    {
        boolean deleted = wishlistService.deleteWish(id);
        wishlistService.deleteWish(id);
        if (deleted)
        {
            redirectAttributes.addFlashAttribute("message", "Wish deleted");
            redirectAttributes.addFlashAttribute("messageType", "success");
        }
        else
        {
            redirectAttributes.addFlashAttribute("message", "Wish did not exist and could therefore not be deleted");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/wishes";
    }

    @PostMapping("/update")
    public String updateWish(@ModelAttribute Wish wish)
    {
        wishlistService.updateWish(wish);
        return "redirect:/wishes";
    }

}

