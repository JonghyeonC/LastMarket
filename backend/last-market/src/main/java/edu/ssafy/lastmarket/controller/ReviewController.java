package edu.ssafy.lastmarket.controller;

import edu.ssafy.lastmarket.argumentresolver.Login;
import edu.ssafy.lastmarket.domain.dto.ReviewDTO;
import edu.ssafy.lastmarket.domain.dto.ReviewPostDTO;
import edu.ssafy.lastmarket.domain.entity.BuyerReview;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.SellerReview;
import edu.ssafy.lastmarket.service.BuyerReviewService;
import edu.ssafy.lastmarket.service.SellerReviewService;
import edu.ssafy.lastmarket.service.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {
    private final SellerReviewService sellerReviewService;
    private final BuyerReviewService buyerReviewService;
    private final TradeService tradeService;

    @PostMapping("/reviews")
    public ResponseEntity<?> saveReview(@Login Member loginMember, @RequestBody ReviewPostDTO reviewPostDTO) {
        boolean isSeller = tradeService.isSeller(reviewPostDTO.getTradeId(), loginMember);
        boolean isBuyer = tradeService.isBuyer(reviewPostDTO.getTradeId(), loginMember);

        if (isSeller) {
            log.info("[{}]save review for seller", reviewPostDTO.getTradeId());
            sellerReviewService.saveSellerReview(loginMember, reviewPostDTO);
        } else if (isBuyer) {
            log.info("[{}]save review for buyer", reviewPostDTO.getTradeId());
            buyerReviewService.saveBuyerReview(loginMember, reviewPostDTO);
        } else {
            throw new IllegalArgumentException("리뷰를 남길 수 없습니다.");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/reviews")
    public ResponseEntity<?> getReceiveReview(@Login Member login) {
        List<SellerReview> sellerReviews = sellerReviewService.getSellerReviewList(login);
        List<BuyerReview> buyerReviews = buyerReviewService.getBuyerReviewList(login);

        List<ReviewDTO> reviews = sellerReviews.stream()
                .map(review -> ReviewDTO.builder()
                        .reviewId(review.getId())
                        .buyerId(review.getBuyer().getId())
                        .buyerNickname(review.getBuyer().getNickname())
                        .reviewTemplate(review.getReviewTemplate())
                        .build())
                .collect(Collectors.toList());
        buyerReviews.stream()
                .map(review -> ReviewDTO.builder()
                        .reviewId(review.getId())
                        .buyerId(review.getBuyer().getId())
                        .buyerNickname(review.getBuyer().getNickname())
                        .reviewTemplate(review.getReviewTemplate())
                        .build())
                .forEach(reviews::add);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
