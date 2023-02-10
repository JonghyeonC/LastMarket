package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.dto.TradeListDTO;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.exception.NotYourAuthority;
import edu.ssafy.lastmarket.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class TradeController {

    private final TradeService tradeService;

    @GetMapping("/trades/sell")
    public ResponseEntity<?> getTradeSellList(@Login Member member, Pageable pageable) {
        if (member == null) {
            throw new NotYourAuthority();
        }

        Map<String, Object> result = new HashMap<>();
        List<TradeListDTO> list = tradeService.getTradeBySeller(member, pageable);
        result.put("trades", list);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/trades/buy")
    public ResponseEntity<?> getTradeBuyList(@Login Member member, Pageable pageable) {
        if (member == null) {
            throw new NotYourAuthority();
        }
        Map<String, Object> result = new HashMap<>();
        List<TradeListDTO> list = tradeService.getTradeByBuyer(member, pageable);
        result.put("trades", list);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
