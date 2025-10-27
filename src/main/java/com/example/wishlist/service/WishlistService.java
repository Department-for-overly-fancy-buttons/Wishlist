package com.example.wishlist.service;

import com.example.wishlist.model.Wish;
import com.example.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService
{
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository)
    {
        this.wishlistRepository = wishlistRepository;
    }

    public Wish addWish(Wish wish)
    {
        if (wish != null
            && wish.getName() != null
            && !wish.getName().isEmpty()
            && wishlistRepository.getWish(wish.getId()) == null)
        {
            return wishlistRepository.addWish(wish);
        }

        return null;
    }

    public List<Wish> getAllWishes()
    {
        return wishlistRepository.getAllWishes();
    }

    public Wish getWish(int id)
    {
        return wishlistRepository.getWish(id);
    }

    public Wish getName(String name)

    public boolean deleteWish(int id)
    {
        return wishlistRepository.deleteWishById(id) > 0;
    }

    public void updateWish(Wish wish)
    {
        wishlistRepository.updateWish(wish);
    }
}
