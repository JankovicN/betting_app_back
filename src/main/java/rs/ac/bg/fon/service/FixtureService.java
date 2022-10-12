package rs.ac.bg.fon.service;

import rs.ac.bg.fon.entity.Fixture;

import java.util.List;


public interface FixtureService {

    Fixture save(Fixture fixture);

    List<Fixture> getNotStarted();


    List<Fixture> getNotStartedByLeague(int league);
}
