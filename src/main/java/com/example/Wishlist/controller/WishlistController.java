package com.example.Wishlist.controller;

public class WishlistController
{
    @GetMapping("/my_wishlists")
    public String viewAllWishlist(Model model) {
        model.addAttribute("wishlists", wishlistService.getAllWishlists());
        return "view-wishlists";
    }

    //skal required være lig false?
    @GetMapping("{name}")
    public String showWishlist(@PathVariable(required = false) String name, Model model) {
        Wishlist wishlist = wishlistService.getWishlist(name);
        if (wishlist != null) {
            model.addAttribute("wishlist", wishlist);
            return "view-wishlist";
        }
        return "redirect:/"; //create fail state
    }

}
