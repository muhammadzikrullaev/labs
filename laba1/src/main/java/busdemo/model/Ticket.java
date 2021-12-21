package busdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Data
@Builder
@AllArgsConstructor
public class Ticket {
    int id;
    String passengerNo;
    String passengerName;
    String passengerLastName;
    int routeId;
    int carParkBusNo;
    String seatNo;
    BigDecimal cost;

    @Override
    public String toString() {
        return "Id:                    " + id +
                "\nPassenger number       " + passengerNo +
                "\nName:                  " + passengerName +
                "\nSurname:               " + passengerLastName +
                "\nRoute id:              " + routeId +
                "\nNumber of railway car: " + carParkBusNo +
                "\nSeat number:           " + seatNo +
                "\nPrice:                 " + cost;
    }
}
