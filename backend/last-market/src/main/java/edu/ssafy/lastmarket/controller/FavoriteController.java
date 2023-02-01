package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.service.FavoriteService;
import edu.ssafy.lastmarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final ProductService productService;

    @GetMapping("/favorite")
    public ResponseEntity<?> getFavoriteList(@Login Member member){
        log.info("favorite ======= {}", member.getId());
        List<ProductListDto> result = favoriteService.getFavorites(member);


        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/favorite/{productId}")
    public ResponseEntity<?> saveFavorite(@Login Member member, @PathVariable("productId")Long id){
        Optional<Product> productOptional = productService.findProductMemberById(id);
        favoriteService.saveFavorite(member,productOptional);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @DeleteMapping("/favorite/{productId}")
    public ResponseEntity<?> deleteFavorite(@Login Member member, @PathVariable("productId")Long id){
        Optional<Product> productOptional = productService.findProductMemberById(id);
        favoriteService.deleteFavorite(member,productOptional);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
