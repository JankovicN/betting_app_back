package rs.ac.bg.fon.service;

import org.springframework.stereotype.Service;
import rs.ac.bg.fon.entity.Bet;


public interface BetService {

    Bet saveBetGroup(Bet bet);

    Bet save(Bet bet);

    void upadateAllBets();
}
