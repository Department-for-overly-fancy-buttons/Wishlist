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

    public Wish getWish(String name)
    {
        String sql = "SELECT name, description, url FROM wish WHERE name = ?";
        List<Wish> result = jdbcTemplate.query(sql, wishRowMapper, name);
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

    public void deleteWish(String name)
    {
        String delete = "delete from wish where name = ?";
        jdbcTemplate.update(delete, name);
    }
}
