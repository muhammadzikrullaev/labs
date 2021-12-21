package com.mzik.laba5.ui;

import com.mzik.laba5.model.Ticket;
import com.mzik.laba5.service.CurrentLocaleService;
import com.mzik.laba5.service.RouteService;
import com.mzik.laba5.service.TicketService;
import com.mzik.laba5.service.exception.AppException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class Commands {

    private final TicketService ticketService;
    private final RouteService routeService;
    private final IO io;
    private final CurrentLocaleService currentLocaleService;
    @Getter
    private ShellState state = ShellState.MAIN_MENU;
    private Ticket handlingTicket;


    @ShellMethod(value = "change current language", key = {"language", "lang"})
    @ShellMethodAvailability("availableInMainMenu")
    public void setLanguage(String language) {
        try {
            currentLocaleService.set(language.strip().toLowerCase());
        } catch (AppException e) {
            io.interPrintln(e.getMessage(), e.getParams());
        }
    }

    @ShellMethod(value = "show all tickets", key = "ticket-all")
    @ShellMethodAvailability("availableInMainMenu")
    public void showAllTickets() {
        var tickets = ticketService.findAll();
        if (tickets.isEmpty()) {
            io.interPrintln("no-ticket-found");
        } else {
            io.println(objectsToString(tickets));
        }
    }


    @ShellMethod(value = "insert new ticket", key = "ticket-insert")
    @ShellMethodAvailability("availableInMainMenu")
    public void insertTicket() {
        handlingTicket = Ticket.buildNullTicket();
//        handlingTicket = new Ticket(0, "", "",
//                "", 0, 0, "", BigDecimal.valueOf(0), );
        state = ShellState.PROCESSING_TICKET;
        show();
    }

    @ShellMethod(value = "show all routes", key = "all-routes")
    public void showAllRoutes() {
        var routes = routeService.findAll();
        if (routes.isEmpty()) {
            io.interPrintln("no-route-found");
        } else {
            io.println(objectsToString(routes));
        }
    }

    @ShellMethod(value = "update ticket by passenger name", key = "ticket-update")
    @ShellMethodAvailability("availableInMainMenu")
    public void updateTicket(@ShellOption(defaultValue = "") String name) {
        if (name.isBlank()) {
            io.interPrint("print-passenger-name");
            name = io.readLine();
        }
        if (name.isBlank()) {
            io.interPrintln("operation-cancelled-by-empty-line");
        } else {
            var tickets = ticketService.findByName(name);
            if (tickets.isEmpty())
                io.interPrintln("no-ticket-found");
            else if (tickets.size() > 1) {
                io.println(objectsToString(tickets));
                io.interPrint("too-many-tickets-found", tickets.size());
                var id = io.nextInt();
                findByIdFromCollectionAndProcess(tickets, id);
            } else {
                handlingTicket = tickets.iterator().next();
                state = ShellState.PROCESSING_TICKET;
                show();
            }
        }
    }

    @ShellMethod(value = "delete ticket by id", key = "delete-by-id")
    @ShellMethodAvailability("availableInMainMenu")
    public void deleteById(@ShellOption(defaultValue = "") String id) {
        if (id.isBlank()) {
            io.interPrintln("set-id");
            id = io.readLine();
        }
        try {
            var intId = Integer.parseInt(id);
            ticketService.deleteById(intId);
            io.interPrintln("operation-successful");
        } catch (NumberFormatException e) {
            io.interPrintln("try-again");
        }


    }

    @ShellMethod(value = "find ticket by passenger name", key = "find-by-name")
    @ShellMethodAvailability("availableInMainMenu")
    public void findByName(@ShellOption(defaultValue = "") String name) {
        if (name.isBlank()) {
            io.interPrint("print-passenger-name");
            name = io.readLine();
        }
        if (name.isBlank()) {
            io.interPrintln("operation-cancelled-by-empty-line");
        } else {
            var tickets = ticketService.findByName(name);
            if (tickets.isEmpty())
                io.interPrintln("no-ticket-found");
            else if (tickets.size() > 1) {
                io.println(objectsToString(tickets));
            } else {
                handlingTicket = tickets.iterator().next();
                show();
            }
        }
    }

    @ShellMethod(value = "find ticket by passenger id", key = "find-by-id")
    @ShellMethodAvailability("availableInMainMenu")
    public void findById(@ShellOption(defaultValue = "") String id) {
        if (id.isBlank()) {
            io.interPrint("print-passenger-id");
            id = io.readLine();
        }
        if (id.isBlank()) {
            io.interPrintln("operation-cancelled-by-empty-line");
        } else {
            handlingTicket = ticketService.findById(Integer.parseInt(id));
            show();
        }
    }

    @ShellMethod(value = "show handling book", key = "show")
    @ShellMethodAvailability("availableInUpdatingTicket")
    private void show() {
        io.interPrintln("set-route");
        io.println(handlingTicket);
    }

    @ShellMethod(value = "set passenger number", key = "set-passenger-number")
    @ShellMethodAvailability("availableInUpdatingTicket")
    public void setPassengerNumber(@ShellOption(defaultValue = "") String passengerNumber) {
        if (passengerNumber.isBlank()) {
            io.interPrint("set-passenger-number");
            io.println("Examples: QYAQDE 555321");
            passengerNumber = io.readLine();
        }
        if (passengerNumber.isBlank()) {
            io.interPrintln("operation-cancelled-by-empty-line");
        } else {
            handlingTicket.setPassengerNo(passengerNumber);
            io.interPrintln("new-passenger-number-is", handlingTicket.getPassengerNo());
        }
    }

    @ShellMethod(value = "set passenger name", key = "set-passenger-name")
    @ShellMethodAvailability("availableInUpdatingTicket")
    public void setPassengerName(@ShellOption(defaultValue = "") String passengerName) {
        if (passengerName.isBlank()) {
            io.interPrint("set-passenger-name");
            passengerName = io.readLine();
        }
        if (passengerName.isBlank()) {
            io.interPrintln("operation-cancelled-by-empty-line");
        } else {
            handlingTicket.setPassengerName(passengerName);
            io.interPrintln("new-passenger-name-is", handlingTicket.getPassengerName());
        }
    }

    @ShellMethod(value = "set passenger last name", key = "set-passenger-last-name")
    @ShellMethodAvailability("availableInUpdatingTicket")
    public void setPassengerLastName(@ShellOption(defaultValue = "") String passengerLastName) {
        if (passengerLastName.isBlank()) {
            io.interPrintln("set-passenger-last-name");
            passengerLastName = io.readLine();
        }
        if (passengerLastName.isBlank()) {
            io.interPrintln("operation-cancelled-by-empty-line");
        } else {
            handlingTicket.setPassengerLastName(passengerLastName);
            io.interPrintln("new-passenger-last-name-is", handlingTicket.getPassengerLastName());
        }
    }


    //TODO make route with meaning from database
    @ShellMethod(value = "set route id", key = "set-route-id")
    @ShellMethodAvailability("availableInUpdatingTicket")
    public void setRouteId(@ShellOption(defaultValue = "") String routeId) {
        if (routeId.isBlank()) {
            io.interPrintln("set-route-id");
            routeId = io.readLine();
        }
        var intRouteId = Integer.parseInt(routeId);
        if (intRouteId > 10 || intRouteId < 1) {
            io.interPrintln("invalid-route-id");
            io.interPrintln("try-again");
            routeId = io.readLine();
            intRouteId = Integer.parseInt(routeId);
        }
        if (intRouteId > 10 || intRouteId < 1) {
            io.interPrintln("invalid-route-id");
            io.interPrintln("operation-cancelled");
        } else {
            handlingTicket.setRouteId(intRouteId);
            io.interPrintln("new-route-id-is", handlingTicket.getRouteId());
        }
    }

    @ShellMethod(value = "set bus number", key = "set-bus-number")
    @ShellMethodAvailability("availableInUpdatingTicket")
    public void setBusNumber(@ShellOption(defaultValue = "") String BusNumber) {
        if (BusNumber.isBlank()) {
            io.interPrintln("set-bus-number");
            BusNumber = io.readLine();
        }
        var intBusNumber = Integer.parseInt(BusNumber);
        if (intBusNumber > 10 || intBusNumber < 1) {
            io.interPrintln("invalid-bus-number-id");
            io.interPrintln("try-again");
            BusNumber = io.readLine();
            intBusNumber = Integer.parseInt(BusNumber);
        }
        if (intBusNumber > 10 || intBusNumber < 1) {
            io.interPrintln("invalid-bus-number-id");
            io.interPrintln("operation-cancelled");
        } else {
            handlingTicket.setCarParkBusNo(intBusNumber);
            io.interPrintln("new-bus-number-is", handlingTicket.getCarParkBusNo());
        }
    }

    @ShellMethod(value = "set seat value", key = "set-seat-number")
    @ShellMethodAvailability("availableInUpdatingTicket")
    public void setSeatValue(@ShellOption(defaultValue = "") String seatNumber) {
        if (seatNumber.isBlank()) {
            io.interPrintln("invalid-seat-number-id");
            io.interPrintln("try-again");
            seatNumber = io.readLine();
        }
        if (seatNumber.isBlank()) {
            io.interPrintln("invalid-seat-number-id");
            io.interPrintln("operation-cancelled");
        } else {
            handlingTicket.setSeatNo(seatNumber);
            io.interPrintln("new-seat-number-is", handlingTicket.getSeatNo());
        }
    }

    @ShellMethod(value = "set ticket cost", key = "set-cost")
    @ShellMethodAvailability("availableInUpdatingTicket")
    public void setCost(@ShellOption(defaultValue = "") String cost) {
        if (cost.isBlank()) {
            io.interPrintln("set-cost");
            cost = io.readLine();
        }
        var bigDecimalCost = BigDecimal.valueOf(Long.parseLong(cost));
        if (bigDecimalCost.equals(BigDecimal.valueOf(0))) {
            io.interPrintln("invalid-cost");
            io.interPrintln("try-again");
            cost = io.readLine();
            bigDecimalCost = BigDecimal.valueOf(Long.parseLong(cost));
        }
        if (bigDecimalCost.equals(BigDecimal.valueOf(0))) {
            io.interPrintln("invalid-cost");
            io.interPrintln("operation-cancelled");
        } else {
            handlingTicket.setCost(bigDecimalCost);
            io.interPrintln("new-cost-is", handlingTicket.getCost());
        }
    }

    @ShellMethod(value = "perform current operation", key = "perform")
    @ShellMethodAvailability("availableInUpdatingTicket")
    public void perform() {
        try {
            if (checkHandlingTicket()) {
                ticketService.save(handlingTicket);
                io.interPrintln("operation-successful");
                state = ShellState.MAIN_MENU;
            }
        } catch (AppException e) {
            io.interPrintln(e.getMessage(), e.getParams());
            io.interPrintln("some-went-wrong");
        }
    }

    @ShellMethod(value = "cancel current operation", key = "cancel")
    @ShellMethodAvailability("availableInUpdatingTicket")
    public void cancel() {
        io.interPrintln("operation-cancelled");
        state = ShellState.MAIN_MENU;
    }

    private void findByIdFromCollectionAndProcess(Collection<Ticket> tickets, int id) {
        tickets.stream()
                .filter(ticket -> ticket.getId() == id)
                .findFirst()
                .ifPresentOrElse(
                        ticket -> {
                            handlingTicket = ticket;
                            show();
                            state = ShellState.PROCESSING_TICKET;
                        },
                        () -> io.interPrintln("select-wrong-id"));
    }

    private Availability availableInMainMenu() {
        return state == ShellState.MAIN_MENU ? Availability.available()
                : Availability.unavailable("available in " + ShellState.MAIN_MENU.getTitle() +
                " only, you now in " + state.getTitle());
    }

    private Availability availableInUpdatingTicket() {
        return state == ShellState.PROCESSING_TICKET ? Availability.available()
                : Availability.unavailable("available in " + ShellState.PROCESSING_TICKET.getTitle() +
                " only, you now in " + state.getTitle());
    }

    private boolean checkHandlingTicket() {
        if (handlingTicket.getPassengerNo().isBlank()) {
            throw new AppException("ticket.check.passenger-no-must-not-be-empty");
        }
        if (handlingTicket.getPassengerName().isBlank()) {
            throw new AppException("ticket.check.name-must-be-set");
        }
        if (handlingTicket.getPassengerLastName().isBlank()) {
            throw new AppException("ticket.check.last-name-must-be-set");
        }
        if (handlingTicket.getRouteId() == 0) {
            throw new AppException("ticket.check.route-id-must-be-set");
        }
        if (handlingTicket.getCarParkBusNo() == 0) {
            throw new AppException("ticket.check.car_bus-no-must-be-set");
        }
        if (handlingTicket.getSeatNo().isBlank()) {
            throw new AppException("ticket.check.seat-no-must-not-be-empty");
        }
        if (handlingTicket.getCost().equals(BigDecimal.valueOf(0))) {
            throw new AppException("ticket.check.cost-must-be-set");
        }
        return true;
    }

    private <T> String objectsToString(Collection<T> objects) {
        return objects.stream()
                .map(T::toString)
                .collect(Collectors.joining("\n\n=============================\n\n"));

    }
}
