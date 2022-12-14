package rs.ac.bg.fon.controller;

import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.ac.bg.fon.entity.BetGroup;
import rs.ac.bg.fon.entity.Fixture;
import rs.ac.bg.fon.entity.Odd;
import rs.ac.bg.fon.service.FixtureService;
import rs.ac.bg.fon.service.OddService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/odd")
public class OddController {
    private final OddService oddService;
    private final FixtureService fixtureService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/set/odds")
    public ResponseEntity<?> getOddsFromAPI(Authentication auth) {
        ArrayList<Fixture> fixtures = (ArrayList<Fixture>) fixtureService.getNotStarted();
        for (Fixture fix : fixtures) {
            try (FileReader in = new FileReader("Odds.json")) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonObject responseEl = gson.fromJson(in, JsonObject.class);
                System.out.println(responseEl);
                JsonArray arr = responseEl.getAsJsonObject().getAsJsonArray("bets");
                // ODD - odd_id(auto), name , odd(kvota), betGroupid, fixtureid

                for (JsonElement betgrp : arr) {
                    int groupId = betgrp.getAsJsonObject().get("id").getAsInt();
                    String name = betgrp.getAsJsonObject().get("name").getAsString();
                    BetGroup currentGroup = new BetGroup();
                    currentGroup.setId(groupId);
                    currentGroup.setName(name);
                    JsonArray oddValues = betgrp.getAsJsonObject().getAsJsonArray("odds");
                    for (JsonElement oddvalue : oddValues) {
                        Odd odd = new Odd();
                        odd.setFixture(fix);
                        fix.getOdds().add(odd);
                        odd.setBetGroup(currentGroup);
                        currentGroup.getOdds().add(odd);
                        odd.setName(oddvalue.getAsJsonObject().get("value").getAsString());
                        String oddString = oddvalue.getAsJsonObject().get("odd").getAsString();
                        BigDecimal oddDecimal = new BigDecimal(oddString).setScale(2, RoundingMode.CEILING);
                        odd.setOdd(oddDecimal);
                        oddService.save(odd);
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                System.out.println(e.getMessage() + "\n" + e);
            }
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/odds")
    public ResponseEntity<List<Odd>> getOddsForFixtures(){
        return ResponseEntity.ok().body(oddService.getALlOdds());
    }

    @GetMapping("/get/odds/{fixture}")
    public ResponseEntity<List<Odd>> getOddsForFixture(@PathVariable int fixture){
        return ResponseEntity.ok().body(oddService.getOddsForFixture(fixture));
    }

}
