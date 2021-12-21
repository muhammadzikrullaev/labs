package com.mzik.service;

import com.mzik.model.Ticket;
import com.mzik.repository.RouteRepository;
import com.mzik.repository.TicketRepository;
import com.mzik.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class TicketService implements DaoService<Ticket> {

    private final TicketRepository ticketRepository;
    private final RouteRepository routeRepository;

    @Override
    public Collection<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket findById(int id) {
        return ticketRepository.findById(id).orElseThrow(
                () -> new ServiceException("No such Ticket!")
        );
    }

    @Override
    public void save(Ticket object) {
        if (routeIdIsValid(object.getRouteId())) {
            if (object.getId() == 0) {
                ticketRepository.save(object);
            } else {
                ticketRepository.update(object);
            }
        }
    }

    @Override
    public Collection<Ticket> findByName(String name) {
        return ticketRepository.findByName(name);
    }

    @Override
    public void deleteById(int id) {
        ticketRepository.deleteById(id);
    }

    private boolean routeIdIsValid(int routeId) {
        var routeIds = routeRepository.getIds();
        return routeIds.contains(routeId);
    }

}
