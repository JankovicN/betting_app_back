package rs.ac.bg.fon.controller;

import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.ac.bg.fon.entity.*;
import rs.ac.bg.fon.service.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/")
public class FootballApiController {

    private final LeagueService leagueService;
    private final FixtureService fixtureService;
    private final BetGroupService betGroupService;
    private final TeamService teamService;
    private final OddService oddService;
    HashMap<Integer, String> leagues = new HashMap<>();
    HashMap<Integer, String> betgroups = new HashMap<>();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/leagues")
    public ResponseEntity<?> getLeaguesFromAPI(Authentication auth) {
        leagues.put(78, "League");
        leagues.put(140, "League");
        leagues.put(61, "League");
        leagues.put(39, "League");
        leagues.put(135, "League");
        leagues.put(2, "League");
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/leagues"))
                    .header("X-RapidAPI-Key", "").header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody()).build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement responseEl = gson.fromJson(responseBody, JsonElement.class);
            JsonArray arr = responseEl.getAsJsonObject().getAsJsonArray("response");
            for (JsonElement jsonElement : arr) {
                int leagueID = jsonElement.getAsJsonObject().get("league").getAsJsonObject().get("id").getAsInt();
                String leagueName = jsonElement.getAsJsonObject().get("league").getAsJsonObject().get("name").getAsString();
                League league = new League(leagueID, leagueName, new ArrayList<>());
                if (leagues.containsKey(leagueID)) {
                    leagueService.save(league);
                }
            }
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/betGroups")
    public ResponseEntity<?> getBetGroupsFromAPI(Authentication auth) {
        betgroups.put(1, "betGrp");
        betgroups.put(5, "betGrp");
        betgroups.put(8, "betGrp");
        betgroups.put(10, "betGrp");
        betgroups.put(12, "betGrp");
        betgroups.put(21, "betGrp");
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api-football-v1.p.rapidapi.com/v3/odds/bets"))
                    .header("X-RapidAPI-Key", "").header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody()).build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            String responseBody = response.body();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement responseEl = gson.fromJson(responseBody, JsonElement.class);
            JsonArray arr = responseEl.getAsJsonObject().getAsJsonArray("response");
            for (JsonElement jsonElement : arr) {
                int betgrpID = jsonElement.getAsJsonObject().get("id").getAsInt();
                if (betgroups.containsKey(betgrpID)) {
                    String betgrpName = jsonElement.getAsJsonObject().get("name").getAsString();
                    BetGroup group = new BetGroup(betgrpID, betgrpName, new ArrayList<>());
                    betGroupService.saveBetGroup(group);
                }

            }
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/odds")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/fixtures")
    public ResponseEntity<?> getFixturesFromAPI(Authentication auth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime before = today.minusDays(2);
        String beforeString = formatter.format(before);
        LocalDateTime after = today.plusDays(10);
        String afterString = formatter.format(after);
        ArrayList<League> allLeagues = (ArrayList<League>) leagueService.getAllLeagues();
        try {
            for (League league : allLeagues) {
                System.out.println("request created");
                String uriString = "https://api-football-v1.p.rapidapi.com/v3/fixtures?league=" + league.getId() + "&season=2022&from=" + beforeString + "&to=" + afterString + "";
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uriString)).header("X-RapidAPI-Key", "")
                        .header("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com").method("GET", HttpRequest.BodyPublishers.noBody()).build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());

                System.out.println("received response");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonElement responseBodyJSON = gson.fromJson(response.body(), JsonElement.class);
                JsonArray arr = responseBodyJSON.getAsJsonObject().getAsJsonArray("response");
                System.out.println("response Array  length");
                for (JsonElement jsonElement : arr) {
                    System.out.println("response array element");
                    int leagueID = jsonElement.getAsJsonObject().get("league").getAsJsonObject().get("id").getAsInt();

                    System.out.println("got league id");
                    if (leagueID == league.getId()) {
                        System.out.println("League id: " + leagueID);
                        JsonObject teams = jsonElement.getAsJsonObject().get("teams").getAsJsonObject();
                        System.out.println("Teams: " + teams);
                        Team home = new Team();
                        home.setId(teams.get("home").getAsJsonObject().get("id").getAsInt());
                        home.setName(teams.get("home").getAsJsonObject().get("name").getAsString());
                        Team away = new Team();
                        away.setId(teams.get("away").getAsJsonObject().get("id").getAsInt());
                        away.setName(teams.get("away").getAsJsonObject().get("name").getAsString());

                        teamService.save(home);
                        teamService.save(away);
                        JsonObject fixtureJson = jsonElement.getAsJsonObject().get("fixture").getAsJsonObject();
                        System.out.println("Fixture json: " + fixtureJson);
                        Fixture fixture = new Fixture();
                        league.getFixtures().add(fixture);
                        home.getHome().add(fixture);
                        away.getAway().add(fixture);
                        fixture.setHome(home);
                        fixture.setAway(away);
                        fixture.setId(fixtureJson.get("id").getAsInt());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String date = fixtureJson.get("date").getAsString();
                        date = date.substring(0, date.indexOf("+"));
                        date = date.replace("T", " ");
                        Date formattedDate = format.parse(date);
                        fixture.setDate(formattedDate);
                        fixture.setLeague(league);
                        fixture.setState(fixtureJson.get("status").getAsJsonObject().get("short").getAsString());
                        JsonObject goals = jsonElement.getAsJsonObject().get("goals").getAsJsonObject();
                        if (!goals.get("home").isJsonNull()) {
                            fixture.setHomeGoals(goals.get("home").getAsInt());
                        }
                        if (!goals.get("away").isJsonNull()) {
                            fixture.setAwayGoals(goals.get("away").getAsInt());
                        }
                        fixture.setOdds(new ArrayList<>());
                        System.out.println("Created fixture: " + fixture);
                        fixtureService.save(fixture);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n" + e);
        }
        return ResponseEntity.ok().build();
    }


}
