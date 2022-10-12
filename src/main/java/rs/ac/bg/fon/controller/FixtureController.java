package rs.ac.bg.fon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.ac.bg.fon.entity.Fixture;
import rs.ac.bg.fon.jsonObjects.FixtureList;
import rs.ac.bg.fon.service.FixtureService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/fixture")
public class FixtureController {


    private final FixtureService fixtureService;

    @GetMapping("/ns/{league}")
    public ResponseEntity<List<Fixture>> getAllFixtures(@PathVariable int league) {
        List<Fixture> fixtures = fixtureService.getNotStartedByLeague(league);
        return ResponseEntity.ok().body(fixtures);
    }


    @GetMapping("/fixtures")
    public ResponseEntity<List<FixtureList>> getFixtures() {
        List<Fixture> fixtures = fixtureService.getNotStarted();
        List<FixtureList> formatedFixtures = new ArrayList<>();
        for (Fixture f : fixtures) {
            if(formatedFixtures.size()==0){
                FixtureList flist = new FixtureList();
                flist.setLeague(f.getLeague());
                flist.addFixtures(fixtures);
                formatedFixtures.add(flist);
            }else{
                boolean containsLeague=false;
                for (FixtureList formatedF: formatedFixtures) {
                    if(formatedF.isLeague(f.getLeague().getId())){
                        containsLeague=true;
                        break;
                    }
                }
                if(!containsLeague){
                    FixtureList flist = new FixtureList();
                    flist.setLeague(f.getLeague());
                    flist.addFixtures(fixtures);
                    formatedFixtures.add(flist);
                }
            }
        }
        for (FixtureList flist:
             formatedFixtures) {
            flist.clearFields();
        }
        return ResponseEntity.ok().body(formatedFixtures);
    }
}
