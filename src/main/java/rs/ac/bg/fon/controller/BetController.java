package rs.ac.bg.fon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.ac.bg.fon.service.BetService;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/bet")
public class BetController {

    private final BetService betService;

    @PatchMapping("/update")
    public ResponseEntity<?> updateAllBets(){
        betService.upadateAllBets();
        return ResponseEntity.ok().build();
    }
}
