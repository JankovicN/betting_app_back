package rs.ac.bg.fon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.entity.League;

public interface LeagueRepository  extends JpaRepository<League, Integer> {
}
