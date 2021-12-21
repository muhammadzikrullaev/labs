package com.mzik.model;

import com.mzik.converter.CarParkConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String routeNo;

    LocalDate departureDate;

    @Convert(converter = CarParkConverter.class)
    CarPark departureCarPark;

    LocalDate arrivalDate;

    @Convert(converter = CarParkConverter.class)
    CarPark arrivalCarPark;

    int busId;

    String status;

//    Collection<Ticket> tickets;


    public String routeToString() {
        return departureCarPark + " -> " + arrivalCarPark;
    }
}
