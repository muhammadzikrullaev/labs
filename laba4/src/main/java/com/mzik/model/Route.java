package com.mzik.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
public class Route {
    int id;
    String routeNo;
    LocalDate departureDate;
    DepartureCarPark departureCarPark;
    LocalDate arrivalDate;
    ArrivalCarPark arrivalCarPark;
    int busId;
    String status;
    Collection<Ticket> tickets;


    public String routeToString() {
        return departureCarPark + " -> " + arrivalCarPark;
    }
}
