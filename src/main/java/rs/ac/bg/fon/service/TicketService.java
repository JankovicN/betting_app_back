package rs.ac.bg.fon.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.entity.Ticket;

import java.util.List;

public interface TicketService{
    Ticket save(Ticket ticket);

    void updateAllTickets();

    List<Ticket> getUserTickets(String username);
}
