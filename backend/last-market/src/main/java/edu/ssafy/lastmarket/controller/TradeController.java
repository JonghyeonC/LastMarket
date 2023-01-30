package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.service.ProductService;
import edu.ssafy.lastmarket.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class TradeController {

    private final TradeService tradeService;
    private final ProductService productService;


    @PostMapping("/trade/{productId}")
    public ResponseEntity<?> saveTrade(@Login Member member, @PathVariable("productId")Long id){

        Optional<Product> productOptional = productService.findProductMemberById(id);
        tradeService.saveTrade(productOptional.get(),productOptional.get().getSeller(),member);
        productService.sellProduct(productOptional.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}
