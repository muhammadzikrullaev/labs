package com.mzik.laba5.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String passengerNo;
    String passengerName;
    String passengerLastName;
    int routeId;
    int carParkBusNo;
    String seatNo;
    BigDecimal cost;
//    Route route;

    @Override
    public String toString() {
//        if (route == null){
//            return "Id:                    " + id +
//                    "\nPassenger number:      " + passengerNo +
//                    "\nName:                  " + passengerName +
//                    "\nSurname:               " + passengerLastName +
//                    "\nRoute id:              " + routeId +
//                    "\nNumber bus:            " + carParkBusNo +
//                    "\nSeat number:           " + seatNo +
//                    "\nPrice:                 " + cost;
//        }
        return "Id:                    " + id +
                "\nPassenger number:      " + passengerNo +
                "\nName:                  " + passengerName +
                "\nSurname:               " + passengerLastName +
//                "\nRoute:                 " + route.routeToString() +
                "\nNumber of bus:           " + carParkBusNo +
                "\nSeat number:           " + seatNo +
                "\nPrice:                 " + cost;
    }

    public static Ticket buildNullTicket() {
        return Ticket.builder()
                .id(0)
                .passengerNo("")
                .passengerName("")
                .passengerLastName("")
                .routeId(0)
                .carParkBusNo(0)
                .seatNo("")
                .cost(BigDecimal.valueOf(0))
//                .route(Route.builder().build())
                .build();
    }
}
