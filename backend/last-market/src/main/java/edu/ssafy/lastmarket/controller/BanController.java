package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.entity.Ban;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.service.BanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ban")
@RequiredArgsConstructor

public class BanController {
    private final BanService banService;

    @PostMapping("/{membername}")
    public ResponseEntity<?> banUser(@Login Member from, @PathVariable String membername) {
        banService.banUser(from, membername);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Ban>> banList(@Login Member from) {
        List<Ban> banList = banService.findBanList(from);
        return new ResponseEntity<>(banList, HttpStatus.OK);
    }
}
