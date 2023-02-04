package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.dto.ReviewDTO;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.service.SellerReviewService;
import edu.ssafy.lastmarket.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
    private final SellerReviewService sellerReviewService;
    private final TradeService tradeService;

    @PostMapping("/reviews")
    public ResponseEntity<?> saveReview(@Login Member loginMember, @RequestBody ReviewDTO reviewDTO) {
        boolean isSeller = tradeService.isSeller(reviewDTO.getTradeId(), loginMember);
        boolean isBuyer = tradeService.isBuyer(reviewDTO.getTradeId(), loginMember);

        if(isSeller){
            sellerReviewService.saveSellerReview(loginMember,reviewDTO);
        } else if(isBuyer){
            //TODO buyer review
        } else {
            throw new IllegalArgumentException("리뷰를 남길 수 없습니다.");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
