package rs.ac.bg.fon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.entity.Odd;
import rs.ac.bg.fon.repository.OddRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OddServiceImpl implements OddService{

    private OddRepository oddRepository;

    @Autowired
    public void setOddRepository(OddRepository oddRepository) {
        this.oddRepository = oddRepository;
    }

    @Override
    public Odd save(Odd odd) {
        return this.oddRepository.save(odd);
    }

    @Override
    public List<Odd> getALlOdds() {
        return oddRepository.findByFixtureState("NS");
    }

    @Override
    public List<Odd> getOddsForFixture(int fixture) {
        return oddRepository.findByFixtureStateAndFixtureId("NS", fixture);
    }
}
