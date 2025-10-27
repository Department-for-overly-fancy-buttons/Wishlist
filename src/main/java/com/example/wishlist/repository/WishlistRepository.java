package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
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

    public List<Wish> getAllWishes()
    {
        String sql = "SELECT name, description, url FROM wish";
        return jdbcTemplate.query(sql, wishRowMapper);
    }

    private final RowMapper<Wish> wishRowMapper = (rs, RowNum) -> new Wish
            (
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("url"),
                    rs.getInt("id")
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
}
