package rs.ac.bg.fon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.entity.Ticket;

import java.util.List;

public interface TicketRepository  extends JpaRepository<Ticket, Integer> {
    @Modifying
    @Query("update Ticket t set t.state = :newState where t.state = :oldState")
    void updateAllTickets(@Param(value = "oldState") String oldState, @Param(value = "newState") String newState);

    List<Ticket> findByUserUsername(String username);
}
