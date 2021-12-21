package busdemo.service;

import busdemo.model.Ticket;
import busdemo.repository.TicketRepository;
import busdemo.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TicketService implements DaoService<Ticket> {

    private final TicketRepository ticketRepository;

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
        if (object.getId() == 0) {
            ticketRepository.save(object);
        } else {
            ticketRepository.update(object);
        }
    }

    @Override
    public Collection<Ticket> findByName(String name) {
        return ticketRepository.findByName(name);
    }

}
