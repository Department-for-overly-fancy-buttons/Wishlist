package com.example.turistguide.repository;

import com.example.turistguide.model.TouristAttraction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TouristAttraction addTouristAttraction(TouristAttraction touristAttraction) {
        if (touristAttraction.getDescription() == null) {
            touristAttraction.setDescription("Ingen oplysninger");
        }
        if (touristAttraction.getCity() == null) {
            touristAttraction.setCity("Ukendt Lokation");
        }
        jdbcTemplate.update("INSERT IGNORE INTO tourist_attractions (name, description, city, ticket_price_in_DKK) VALUES (?,?,?,?)",
                touristAttraction.getName(), touristAttraction.getDescription(),
                touristAttraction.getCity(), touristAttraction.getTicketPriceInDKK());
        for (String tag : touristAttraction.getTags()) {
            String sqlTag = "INSERT IGNORE INTO tags value(?,?)";
            jdbcTemplate.update(sqlTag, touristAttraction.getName(), tag);
        }

        return touristAttraction;
    }

    public List<TouristAttraction> getAllTouristAttraction() {
        String sql = "SELECT * FROM tourist_attractions";
        return jdbcTemplate.query(sql, new TouristAttractionRowMapper());
    }

    public TouristAttraction getTouristAttraction(String name) {
        String sql = "SELECT * FROM tourist_attractions where name = '" + name + "'";
        List<TouristAttraction> attractions = jdbcTemplate.query(sql, new TouristAttractionRowMapper());
        String sqlTags = "select tag.name from tags join tag on tags.tag_name=tag.name where tags.attraction_name='" + name + "'";
        List<String> tags = jdbcTemplate.query(sqlTags, new TagRowMapper());
        if (attractions.size() > 0) {
            TouristAttraction attraction = attractions.get(0);
            attraction.setTags(tags);
            return attraction;
        }
        return null;
    }

    public void updateTouristAttraction(TouristAttraction updatedTouristAttraction) {
        TouristAttraction touristAttraction = getTouristAttraction(updatedTouristAttraction.getName());

//        //check all attributes of TouristAttraction class for updated information
        if (updatedTouristAttraction.getDescription() != null) {
            touristAttraction.setDescription(updatedTouristAttraction.getDescription());
        }
        if (updatedTouristAttraction.getCity() != null) {
            touristAttraction.setCity(updatedTouristAttraction.getCity());
        }
        List<String> updatedTags = updatedTouristAttraction.getTags();
        if (updatedTags != null) { //TODO test denne condition
            touristAttraction.setTags(updatedTouristAttraction.getTags());
        }
        touristAttraction.setTicketPriceInDKK(updatedTouristAttraction.getTicketPriceInDKK());

        jdbcTemplate.update("UPDATE tourist_attractions SET description=?, city=?, ticket_price_in_DKK=? where name=?",
                touristAttraction.getDescription(), touristAttraction.getCity(),
                touristAttraction.getTicketPriceInDKK(), touristAttraction.getName());
        jdbcTemplate.update("DELETE FROM Tags where attraction_name=?", touristAttraction.getName());
        for (String tag : touristAttraction.getTags()) {
            String sqlTag = "INSERT IGNORE INTO tags value(?,?)";
            jdbcTemplate.update(sqlTag, touristAttraction.getName(), tag);
        }

        //resolve update
    }

    public void deleteTouristAttraction(String name) {
        String delete = "delete from tourist_attractions where name = ?";
        jdbcTemplate.update(delete, name);
    }


    public List<String> getCities() {
        String sql = "SELECT * FROM city";
        List<String> cities = jdbcTemplate.query(sql, new CityRowMapper());
        return cities;
    }

    public List<String> getTags() {
        String sql = "SELECT * FROM tag";
        List<String> tags = jdbcTemplate.query(sql, new TagRowMapper());
        return tags;
    }
}
