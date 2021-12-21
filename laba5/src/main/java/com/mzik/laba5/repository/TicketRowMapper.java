package com.mzik.laba5.repository;

import com.mzik.laba5.model.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRowMapper implements RowMapper<Ticket> {
    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Ticket.builder()
                .id(rs.getInt("id"))
                .passengerNo(rs.getString("passenger_no"))
                .passengerName(rs.getString("passenger_name"))
                .passengerLastName(rs.getString("passenger_last_name"))
                .routeId(rs.getInt("route_id"))
                .carParkBusNo(rs.getInt("car_park_bus_no"))
                .seatNo(rs.getString("seat_no"))
                .cost(rs.getBigDecimal("cost"))
                .build();
    }
}
