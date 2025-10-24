package com.example.Wishlist.service;


public class WishlistService
{
    public List<Wishlist> getAllWishlists() {
        return wishListRepository.getAllWishlists();
    }

    public Wishlist getWishlist(String name) {
        Wishlist wishlist = wishlistRepository.findWishlistByName(name);
        List<Wish> wishes = wishlistRepository.getWishes(wishlist.getWishlistId());
        wishlist.setWishes(wishes);
        return wishlist;
    }
}
