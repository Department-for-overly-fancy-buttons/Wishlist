package com.example.Wishlist.repository;

public class WishlistRepository
{

    public List<Wishlist> getAllWishlists() {
        String sql = "SELECT * FROM wishlists";
        return jdbcTemplate.query(sql, new RowMapper());
    }

    //add keymanager to extarct wishlist id
    //add wishlist_id as an atribute in the Wishlist class
    public Wishlist findWishlistByName(String name) {
        String sql = "SELECT * FROM wishlists where name = ?";
        List<Wishlist> wishlists = jdbcTemplate.query(sql, new WishlistRowMapper(),name);
        if (wishlists.size() > 0) {
            Wishlist wishlist = wishlists.get(0);
            return wishlist;
        }
        return null;
    }

    public List<Wish> getWishes(int wishlistId){
        String sql = "SELECT * FROM wishes where wishlist_id = ?";
        List<Wishes> wishes = jdbcTemplate.query(sql, new WishRowMapper(),wishlistId);
        return wishes;
    }


}
