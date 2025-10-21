package com.example.turistguide.repository;

import com.example.turistguide.model.TouristAttraction;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TouristAttractionRowMapper implements RowMapper<TouristAttraction> {
    @Override
    public TouristAttraction mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TouristAttraction(rs.getString("name"),
                rs.getString("description"),
                rs.getString("city"), rs.getDouble("ticket_price_in_DKK"));
    }

}
