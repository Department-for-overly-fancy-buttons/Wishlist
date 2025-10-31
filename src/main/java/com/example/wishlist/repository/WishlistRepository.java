package com.example.wishlist.repository;

import com.example.wishlist.model.Account;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;


@Repository
public class WishlistRepository
{
    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wishlist> getAllWishlists() {
        String sql = "SELECT * FROM wishlists";
        return jdbcTemplate.query(sql, wishlistRowMapper);
    }

    //add keymanager to extarct wishlist id
    //add wishlist_id as an atribute in the Wishlist class
    //Should wishlist be found by nam or by id?
    public Wishlist findWishlistByName(String name) {
        String sql = "SELECT * FROM wishlists where name = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(sql, wishlistRowMapper,name);
        if (wishlists.size() > 0) {
            Wishlist wishlist = wishlists.get(0);
            return wishlist;
        }
        return null;
    }

    public Wish addWish(Wish wish)
    {
        if (wish.getDescription() == null)
        {
            wish.setDescription("Ingen ekstra information om ønsket");
        }
        if (wish.getName() == null)
        {
            wish.setName("Intet navn til ønsket endnu");
        }
        if (wish.getUrl() == null)
        {
            wish.setUrl("Der er desværre ingen link til dette ønske");
        }

        jdbcTemplate.update(
                "INSERT IGNORE INTO wish (name, description, url) VALUES (?,?,?)",
                wish.getName(),
                wish.getDescription(),
                wish.getUrl());

        return wish;
    }

    public Wish getWish(int id)
    {
        String sql = "SELECT name, description, url FROM wish WHERE id = ?";
        List<Wish> result = jdbcTemplate.query(sql, wishRowMapper, id);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Wish> getAllWishes(int wishlistId)
    {
        String sql = "SELECT name, description, url FROM wish WHERE wishlist_id = ?";
        return jdbcTemplate.query(sql, wishRowMapper);
    }

    private final RowMapper<Wish> wishRowMapper = (rs, RowNum) -> new Wish
            (
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("url"),
                    rs.getInt("id")
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

    public int deleteWishById(int id)
    {
        return jdbcTemplate.update("DELETE FROM wish WHERE wishId = ?", id);
    }

    public void updateWish(Wish updatedWish)
    {
        Wish wish = getWish(updatedWish.getId());
        if (updatedWish.getDescription() != null)
        {
            wish.setDescription(updatedWish.getDescription());
        }
        if (updatedWish.getUrl() != null)
        {
            wish.setUrl(updatedWish.getUrl());
        }
        if (updatedWish.getName() != null)
        {
            wish.setName(updatedWish.getName());
        }

        jdbcTemplate.update
                ("UPDATE wish SET description=?, name=?, url=? WHERE id=?",
                wish.getDescription(), wish.getUrl(), wish.getName(), wish.getId());
    }

    public Account addAccount(Account account) {
        String sql = "INSERT INTO Accounts (UserName, Password) VALUES (?, ?)";
        jdbcTemplate.update(sql, account.getUsername(), account.getPassword());
        return account;
    }

    public Account getAccount(Account account) {
        String sql = "SELECT * FROM Accounts where UserName = ?";
        List<Account> accounts = jdbcTemplate.query(sql, accountlistRowMapper,account.getUsername());
        if (accounts.size() > 0) {
            Account foundAccount = accounts.get(0);
            return foundAccount;
        }
        return null;
    }
}
