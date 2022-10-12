package rs.ac.bg.fon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.entity.League;
import rs.ac.bg.fon.repository.LeagueRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LeagueServiceImpl implements LeagueService{

    LeagueRepository leagueRepository;

    @Autowired
    public void setLeagueRepository(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @Override
    public League save(League league) {
        log.info("Saving league: {}", league);
        return leagueRepository.save(league);
    }

    @Override
    public List<League> getAllLeagues(){
        log.info("Getting all leagues!");
        return leagueRepository.findAll();
    }
}
