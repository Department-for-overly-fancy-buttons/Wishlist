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
        try {
            return wishlistRepository.getAllWishlists(accountId);
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to access your wishlists", dataAccessException);
        }
    }

    public Wishlist getWishlist(String title, int ownerId) {
        try {
            Wishlist wishlist = wishlistRepository.findWishlistByName(title, ownerId);
            if (wishlist == null) {
                throw new WishlistNotFoundException("A wishlist of the chosen name does not exist");
            }
            List<Wish> wishes = wishlistRepository.getAllWishes(wishlist.getId());
            wishlist.setWishes(wishes);
            return wishlist;
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to access this wishlist: " + title, dataAccessException);
        }
    }

    public Wish addWish(Wish wish) {
        if (wish != null
                && wish.getName() != null
                && !wish.getName().isBlank()) {
            if (getWish(wish.getName(), wish.getWishlistId()) != null) {
                throw new DuplicateWishException("A wish of the chosen name already exists, please try a different name");
            }
            try {
                return wishlistRepository.addWish(wish);
            }catch (DataAccessException dataAccessException) {
                throw new DatabaseOperationException("A fatal error has occurred while attempting to create wish: ", dataAccessException);
            }
        }

        return null;
    }

    public Wish getWish(String wishName, int wishlistId) {
        return wishlistRepository.getWish(wishName, wishlistId);
    }

    public boolean deleteWish(String wishName, String wishlistTitle, Account account) {
        int wishlistID = getWishlist(wishlistTitle, account.getAccountId()).getId();
        try {
            return wishlistRepository.deleteWishById(wishName, wishlistID) > 0;
        } catch (DataIntegrityViolationException ex) {
            throw new WishNotFoundException("A wish of the chosen name does not exist");
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("The chosen wish has failed to be deleted", dataAccessException);
        }
    }

    public boolean deleteWishlist(int wishlistId) {
        try {
            return wishlistRepository.deleteWishlistById(wishlistId) > 0;
        } catch (DataIntegrityViolationException ex) {
            throw new WishNotFoundException("A wishlist of the chosen title does not exist");
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("The chosen wishlist has failed to be deleted", dataAccessException);
        }
    }

    public void updateWish(Wish updatedWish, String deprecatedName, int wishlistId) {
        Wish wishWithSameName = getWish(updatedWish.getName(), wishlistId);
        if (wishWithSameName != null && wishWithSameName.getId() != getWish(deprecatedName, wishlistId).getId()) {
            System.out.println(wishWithSameName.getId() + " != " + updatedWish.getId());
            throw new DuplicateWishException("A wish of the chosen name already exists");
        }
        Wish deprecatedWish = getWish(deprecatedName, wishlistId);
        try{
        wishlistRepository.updateWish(updatedWish, deprecatedWish);
        }catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to update your wish", dataAccessException);
        }
    }

    public Account addAccount(Account account) {
        if (!account.getPassword().isBlank() && !account.getUsername().isBlank()) {
            try {
                return wishlistRepository.addAccount(account);
            } catch (
                    DataIntegrityViolationException ex) { //DataIntegrityViolationException is thrown when there is an attempt to violate the database schema (in this case the UNIQUE in username)
                throw new DuplicateAccountException("An Account of the chosen username already exists");
            } catch (DataAccessException dataAccessException) {
                throw new DatabaseOperationException("The account has failed to be created", dataAccessException);
            }
        } else {
            return null;
        }
    }


    public Account logIn(Account typedAccount) {
        Account foundAccount = wishlistRepository.getAccount(typedAccount);
        if (foundAccount == null || !foundAccount.getPassword().equals(typedAccount.getPassword())) {
            throw new AccountNotFoundException(typedAccount.getUsername(), typedAccount, foundAccount);
        }
        return foundAccount;
    }

    public Wishlist addWishlist(Wishlist wishlist) {
        List<Wishlist> accountsWishlists = getAllMyWishlists(wishlist.getOwnerId());
        //Ensuring an account can only have one wishlist of the same title
        for (Wishlist accountWishlist : accountsWishlists) {
            if (accountWishlist.getTitle().equalsIgnoreCase(wishlist.getTitle())) {
                throw new DuplicateWishlistException("A wishlist of the chosen name already exists, please try a different name");
            }
        }
        try {
            return wishlistRepository.addWishlist(wishlist);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateWishlistException("A wishlist of the chosen title already exists");
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("The wishlist has failed to be created", dataAccessException);
        }
    }

}
