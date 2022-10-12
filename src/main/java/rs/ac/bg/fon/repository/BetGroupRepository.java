package rs.ac.bg.fon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.bg.fon.entity.BetGroup;

import java.util.List;

public interface BetGroupRepository  extends JpaRepository<BetGroup, Integer> {

    List<BetGroup> findByOddsFixtureId(int fixture);
}
