package com.example.wishlist.controller;

import com.example.wishlist.model.Account;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/create/account")
    public String createAccount(Model model) {
        model.addAttribute("account", new Account());
        return "create-account-form";
    }

    @PostMapping("/create/account")
    public String createNewAccount(@ModelAttribute Account account, HttpSession session){
        Account newAccount = wishlistService.addAccount(account);
        session.setAttribute("account", newAccount);
        return "redirect:/wishes/create-wishlist-form";
    }

    @GetMapping("/login")
    public String logInForm(Model model){
        model.addAttribute("account", new Account());
        return "log-in-form";
    }

    @PostMapping("/login")
    public String LogIn(@ModelAttribute Account account, HttpSession session){
        Account foundAccount = wishlistService.logIn(account);
        session.setAttribute("account", account);
        session.setMaxInactiveInterval(10);
        return "redirect:/wishes";
    }

    @GetMapping("/my_wishlists")
    public String viewAllWishlist(Model model) {
        model.addAttribute("wishlists", wishlistService.getAllWishlists());
        return "view-wishlists";
    }
    @GetMapping()
    public String createWishlist(Model model, HttpSession session){
        if(session.getAttribute("account") == null){
            return "redirect:/";
        }
        model.addAttribute("wishlist", new Wishlist());
        return "create-wishlist-form";
    }

    @PostMapping("/create/wishlist")
    public String createNewWishlist(){
        return "redirect:/";
    }

    //skal required være lig false?
    @GetMapping("{title}")
    public String showWishlist(@PathVariable(required = false) String title, Model model) {
        Wishlist wishlist = wishlistService.getWishlist(title);
        if (wishlist != null) {
            model.addAttribute("wishlist", wishlist);
            return "view-wishlist";
        }
        return "redirect:/"; //create fail state
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

//    @GetMapping("/")
//    public String getAllWishes(Model model)
//    {
//        model.addAttribute("wishes", wishlistService.getAllWishes());
//        return "wish-list";
//    }

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

