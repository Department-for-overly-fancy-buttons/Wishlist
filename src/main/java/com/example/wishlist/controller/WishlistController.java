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
        if(newAccount != null) {
            session.setAttribute("account", newAccount);
            session.setMaxInactiveInterval(180);
            return "redirect:/wishes/my_wishlists";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String logInForm(Model model, HttpSession session){
        if(session.getAttribute("account") == null) {
            model.addAttribute("account", new Account());
            return "log-in-form";
        }else{
            return "redirect:/wishes/my_wishlists";
        }
    }

    @PostMapping("/login")
    public String LogIn(@ModelAttribute Account account, HttpSession session){
        Account foundAccount = wishlistService.logIn(account);
        session.setAttribute("account", foundAccount);
        session.setMaxInactiveInterval(180);
        return "redirect:/wishes/my_wishlists";
    }

    @GetMapping("/my_wishlists")
    public String viewMyWishlist(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if(account == null){
            return "redirect:/";
        }else {
            model.addAttribute("wishlists", wishlistService.getAllMyWishlists(account.getAccountId()));
            return "view_wishlists";
        }
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
    public String createNewWishlist(@ModelAttribute Wishlist wishlist, HttpSession session){
        Account account = (Account) session.getAttribute("account");
        if(account == null){
            return "redirect:/";
        }
        wishlist.setOwnerId(account.getAccountId());
        Wishlist resultingWishlist = wishlistService.addWishlist(wishlist);
        return (resultingWishlist != null) ? "redirect:/wishes/my_wishlists" : "redirect:/";
    }

    //skal required være lig false?
    @GetMapping("{title}/view")
    public String showWishlist(@PathVariable(required = false) String title, Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            Wishlist wishlist = wishlistService.getWishlist(title, account.getAccountId());
            if (wishlist != null) {
                model.addAttribute("wishlist", wishlist);
                session.setAttribute("wishlist", wishlist);
                return "view-wishlist";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/{title}/{name}/view")
    public String showWish(@PathVariable(required = false) String name, Model model, @PathVariable String title, HttpSession session){
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            Wish wish = wishlistService.getWish(name, wishlistService.getWishlistId(title, account.getAccountId()));
            if (wish != null) {
                model.addAttribute("wish", wish);
                model.addAttribute("wishlist", session.getAttribute("wishlist"));
                model.addAttribute("redirectionUrl", title + "/view");
                session.setAttribute("wish", wish);
                return "view-wish";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/{title}/add")
    public String showAddWishForm(Model model, HttpSession session,@PathVariable String title)
    {
        if (session.getAttribute("account") != null) {
            model.addAttribute("wish", new Wish());
            model.addAttribute("redirectionUrl", title + "/view");
            return "add-wish-form";
        }
        return "redirect:/";
    }

    @PostMapping("/save")
    public String addWish(@ModelAttribute Wish wish, Model model, HttpSession session)
    {
        wish.setWishlistId((Integer) ((Wishlist) session.getAttribute("wishlist")).getId());
        Wish resultingWish = wishlistService.addWish(wish);
        return (resultingWish != null) ? "redirect:/wishes/my_wishlists" : "redirect:/";
    }

//    @GetMapping("/")
//    public String getAllWishes(Model model)
//    {
//        model.addAttribute("wishes", wishlistService.getAllWishes());
//        return "wish-list";
//    }

    @GetMapping("{title}/{name}/edit")
    public String showUpdateWishForm(@PathVariable String title, @PathVariable String name, Model model, HttpSession session)
    {
        Account account = (Account) session.getAttribute("account");
        if (account != null) {
            int wishlistId = wishlistService.getWishlistId(title, account.getAccountId());
            Wish wish = wishlistService.getWish(name, wishlistId);
            Wishlist wishlist = wishlistService.getWishlist(title, account.getAccountId());
            if (wish != null && wishlist != null) {
                model.addAttribute("wish", wish);
                model.addAttribute("wishlist", wishlist);

                return "update-wish-form";
            }
        }
        return "redirect:/";
    }

    @PostMapping("{title}/{name}/delete")
    public String deleteWish(@PathVariable String name, RedirectAttributes redirectAttributes, @PathVariable String title, HttpSession session)
    {
        boolean deleted = wishlistService.deleteWish(name, title, (Account) session.getAttribute("account"));
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
        return "redirect:/wishes/" + title + "/view";
    }

    @PostMapping("{title}/{name}/update")
    public String updateWish(@ModelAttribute Wish updatedWish, @PathVariable String title, @PathVariable String name, HttpSession session)
    {
        wishlistService.updateWish(updatedWish, name, wishlistService.getWishlistId(title, ((Account) session.getAttribute("account")).getAccountId()));
        return "redirect:/wishes/" + title + "/view";
    }

}

