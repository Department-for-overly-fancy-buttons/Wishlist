package com.example.wishlist.service;

import com.example.wishlist.exceptions.*;
import com.example.wishlist.model.Account;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.repository.WishlistRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Wishlist> getAllMyWishlists(int accountId) {
        return wishlistRepository.getAllWishlists(accountId);
    }

    public Wishlist getWishlist(String name) {
        Wishlist wishlist = wishlistRepository.findWishlistByName(name);
        List<Wish> wishes = wishlistRepository.getAllWishes(wishlist.getId());
        wishlist.setWishes(wishes);
        return wishlist;
    }

    public Wish addWish(Wish wish) {
        if (wish != null
                && wish.getName() != null
                && !wish.getName().isBlank()) {
            if(wishlistRepository.getWishByName(wish.getName()) != null){
                throw new DuplicateWishException("A wish of the chosen name already exists, please try a different name");
            }
            return wishlistRepository.addWish(wish);
        }

        return null;
    }

    public List<Wish> getAllWishes(int wishlistId) {
        return wishlistRepository.getAllWishes(wishlistId);
    }

    public Wish getWish(int id) {
        return wishlistRepository.getWish(id);
    }

    /*public Wish getName(String name)*/

    public boolean deleteWish(int id) {
        try {
            return wishlistRepository.deleteWishById(id) > 0;
        }catch (DataIntegrityViolationException ex){
            throw new WishNotFoundException("A wish of the chosen name does not exist");
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("The chosen wish has failed to be deleted", dataAccessException);
        }
    }

    public boolean deleteWishlist(int id) {
        return wishlistRepository.deleteWishlistById(id) > 0;
    }

    public void updateWish(Wish wish) {
        wishlistRepository.updateWish(wish);
    }

    public Account addAccount(Account account) {
        if(!account.getPassword().isBlank() && account.getUsername().isBlank()) {
            try {
                return wishlistRepository.addAccount(account);
            } catch (DataIntegrityViolationException ex) { //DataIntegrityViolationException is thrown when there is an attempt to violate the database schema (in this case the UNIQUE in username)
                throw new DuplicateAccountException("An Account of the chosen username already exists");
            } catch (DataAccessException dataAccessException) {
                throw new DatabaseOperationException("The account has failed to be created", dataAccessException);
            }
        }else {
            return null;
        }
    }


    public Account logIn(Account typedAccount) {
        Account foundAccount = wishlistRepository.getAccount(typedAccount);
        if(foundAccount == null || !foundAccount.getPassword().equals(typedAccount.getPassword())){
            throw new AccountNotFoundException(typedAccount.getUsername(), typedAccount, foundAccount);
        }
        return foundAccount;
    }

    public Wishlist addWishlist(Wishlist wishlist) {
        List<Wishlist> accountsWishlists = getAllMyWishlists(wishlist.getOwnerId());
        //Ensuring one account can only have one wishlist of the same title
        for(Wishlist accountWishlist : accountsWishlists){
            if(accountWishlist.getTitle().equalsIgnoreCase(wishlist.getTitle())){
                throw new DuplicateWishlistException("A wishlist of the chosen name already exists, please try a different name");
            }
        }
        return wishlistRepository.addWishlist(wishlist);
    }
}
