package rs.ac.bg.fon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.entity.Bet;

public interface BetRepository  extends JpaRepository<Bet, Integer> {

    @Modifying
    @Query("update Bet b set b.state = :newState where b.state = :oldState")
    void updateAllBets(@Param(value = "oldState") String oldState, @Param(value = "newState") String newState);
}
