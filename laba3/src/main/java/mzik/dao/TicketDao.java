package mzik.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mzik.config.ConnectionManager;
import mzik.entity.Ticket;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TicketDao implements Dao<Ticket> {

    private final ConnectionManager connectionManager;

    @Override
    @SneakyThrows
    public List<Ticket> findAll() {
        log.info("start of dao method find all tickets");
        List<Ticket> tickets = new ArrayList<>();

        try (var connection = connectionManager.get();
             var preparedStatement = connection.prepareStatement("select * from ticket")) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
        }

        log.info("end of find all method");
        return tickets;
    }

    @Override
    @SneakyThrows
    public Optional<Ticket> findById(int ticketId) {
        log.info("start of dao method find ticket by id: {}", ticketId);
        Ticket ticket = null;

        try (var connection = connectionManager.get();
             var preparedStatement = connection.prepareStatement("select * from ticket where id = ?")){
            preparedStatement.setObject(1, ticketId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                ticket = buildTicket(resultSet);
                log.info("found ticket: {}", ticket);
            }
        }
        log.info("end of dao method find ticket by id");
        return Optional.ofNullable(ticket);
    }

    @SneakyThrows
    private Ticket buildTicket(ResultSet resultSet) {
        return Ticket.builder()
                .id(resultSet.getInt("id"))
                .passengerNo(resultSet.getString("passenger_no"))
                .passengerName(resultSet.getString("passenger_name"))
                .passengerLastName(resultSet.getString("passenger_last_name"))
                .routeId(resultSet.getInt("route_id"))
                .carParkBusNo(resultSet.getInt("car_park_bus_no"))
                .seatNo(resultSet.getString("seat_no"))
                .cost(resultSet.getInt("cost"))
                .build();
    }
}
