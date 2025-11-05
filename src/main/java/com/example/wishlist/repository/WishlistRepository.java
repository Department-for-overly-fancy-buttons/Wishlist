package com.example.wishlist.repository;

import com.example.wishlist.model.Account;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class WishlistRepository
{
    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wishlist> getAllWishlists(int accountId) {
        String sql = "SELECT * FROM wishlists WHERE OwnerId = ?";
        return jdbcTemplate.query(sql, wishlistRowMapper, accountId);
    }

    public Wishlist findWishlistByName(String name, int ownerId) {
        String sql = "SELECT * FROM wishlists WHERE title = ? AND OwnerId = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(sql, wishlistRowMapper,name, ownerId);
        if (!wishlists.isEmpty()) {
            return wishlists.get(0);
        }
        return null;
    }

    public Wish addWish(Wish wish)
    {
        if (wish.getDescription() == null)
        {
            wish.setDescription("Ingen ekstra information om ønsket");
        }
        if (wish.getUrl() == null)
        {
            wish.setUrl("Der er desværre ingen link til dette ønske");
        }

        jdbcTemplate.update(
                "INSERT IGNORE INTO wishes (name, description, url, wishlistId) VALUES (?,?,?,?)",
                wish.getName(),
                wish.getDescription(),
                wish.getUrl(),
                wish.getWishlistId());

        return wish;
    }

    public Wish getWish(String wishName, int wishlistId)
    {
        String sql = "SELECT * FROM wishes WHERE Name = ? AND WishlistId = ?";
        List<Wish> result = jdbcTemplate.query(sql, wishRowMapper, wishName, wishlistId);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Wish> getAllWishes(int wishlistId)
    {
        String sql = "SELECT * FROM wishes WHERE wishlistId = ?";
        return jdbcTemplate.query(sql, wishRowMapper, wishlistId);
    }

    private final RowMapper<Wish> wishRowMapper = (rs, RowNum) -> new Wish
            (
                    rs.getInt("WishId"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("url")

            );

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, RowNum) -> new Wishlist
            (
                    rs.getInt("WishlistId"),
                    rs.getString("Title"),
                    rs.getInt("OwnerId")
            );

    private final RowMapper<Account> accountlistRowMapper = (rs, RowNum) -> new Account
            (
                    rs.getInt("AccountId"),
                    rs.getString("UserName"),
                    rs.getString("Password")
            );

    public int deleteWishById(String wishName, int wishlistId)
    {
        return jdbcTemplate.update("DELETE FROM wishes WHERE Name = ? AND WishlistId = ?", wishName, wishlistId);
    }

    public int deleteWishlistById(int id){
        return jdbcTemplate.update("DELETE FROM wishlists WHERE wishId = ?", id);
    }

    public void updateWish(Wish updatedWish, Wish deprecatedWish)
    {

        if (updatedWish.getDescription() != null)
        {
            deprecatedWish.setDescription(updatedWish.getDescription());
        }
        if (updatedWish.getUrl() != null)
        {
            deprecatedWish.setUrl(updatedWish.getUrl());
        }
        if (updatedWish.getName() != null)
        {
            deprecatedWish.setName(updatedWish.getName());
        }

        jdbcTemplate.update
                ("UPDATE wishes SET description=?, name=?, url=? WHERE wishId=?",
                        deprecatedWish.getDescription(), deprecatedWish.getName(), deprecatedWish.getUrl(), deprecatedWish.getId());
    }

    public Account addAccount(Account account) {
        String sql = "INSERT INTO Accounts (UserName, Password) VALUES (?, ?)";
        jdbcTemplate.update(sql, account.getUsername(), account.getPassword());
        return getAccount(account);
    }

    public Account getAccount(Account account) {
        String sql = "SELECT * FROM Accounts WHERE UserName = ?";
        List<Account> accounts = jdbcTemplate.query(sql, accountlistRowMapper,account.getUsername());
        if (!accounts.isEmpty()) {
            return accounts.get(0);
        }
        return null;
    }

    public Wishlist addWishlist(Wishlist wishlist) {
            if (wishlist.getOwnerId() == 0 || wishlist.getTitle().isEmpty())
            {
               return null;
            }

            jdbcTemplate.update(
                    "INSERT IGNORE INTO wishlists (Title, OwnerId) VALUES (?,?)",
                    wishlist.getTitle(),
                    wishlist.getOwnerId());

            return wishlist;
    }
}
