package com.mzik.repository;

import com.mzik.model.Route;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteRowMapper implements RowMapper<Route> {

    @Override
    public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Route.builder()
                .id(rs.getInt("id"))
                .routeNo(rs.getString("route_no"))
                .departureDate(rs.getDate("departure_date").toLocalDate())
//                .departureCarPark(new DepartureCarPark(null, rs.getString("departure_car_park")))
                .arrivalDate(rs.getDate("arrival_date").toLocalDate())
//                .arrivalCarPark(new ArrivalCarPark(null, rs.getString("arrival_car_park")))
                .busId(rs.getInt("bus_id"))
                .status(rs.getString("status"))
                .build();
    }
}
