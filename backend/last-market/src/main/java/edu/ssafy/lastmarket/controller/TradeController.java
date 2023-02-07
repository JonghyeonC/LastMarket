package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.dto.TradeListDTO;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.exception.NotYourAuthority;
import edu.ssafy.lastmarket.service.ProductService;
import edu.ssafy.lastmarket.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class TradeController {

    private final TradeService tradeService;
    private final ProductService productService;


    @PostMapping("/trade/{productId}")
    public ResponseEntity<?> saveTrade(@Login Member member, @PathVariable("productId") Long id) {

        Optional<Product> productOptional = productService.findProductMemberById(id);
        //save Trade
        tradeService.saveTrade(productOptional.get(), member);
        //dealState 변경
        productService.sellProduct(productOptional.get());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/trade/sell")
    public ResponseEntity<?> getTradeSellList(@Login Member member, Pageable pageable) {
        if (member == null) {
            throw new NotYourAuthority();
        }

        Map<String, Object> result = new HashMap<>();
        List<TradeListDTO> list = tradeService.getTradeBySeller(member, pageable);
        result.put("trades", list);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/trade/buy")
    public ResponseEntity<?> getTradeBuyList(@Login Member member, Pageable pageable) {
        if (member == null) {
            throw new NotYourAuthority();
        }
        Map<String, Object> result = new HashMap<>();
        List<TradeListDTO> list = tradeService.getTradeByBuyer(member, pageable);
        result.put("trades",list);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
