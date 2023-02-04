package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ReviewDTO;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.SellerReview;
import edu.ssafy.lastmarket.domain.entity.Trade;
import edu.ssafy.lastmarket.exception.ReviewAlreadyExistException;
import edu.ssafy.lastmarket.repository.SellerReviewRepository;
import edu.ssafy.lastmarket.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerReviewServiceImpl implements SellerReviewService {
    private final SellerReviewRepository sellerReviewRepository;
    private final TradeRepository tradeRepository;

    @Override
    @Transactional
    public SellerReview saveSellerReview(Member reviewer, ReviewDTO reviewDTO) {
        Trade trade = tradeRepository.findById(reviewDTO.getTradeId())
                .orElseThrow(() -> new IllegalArgumentException("없는 거래입니다."));

        boolean exist = sellerReviewRepository.existsBySellerAndTrade(reviewer, trade);
        if (exist) {
            throw new ReviewAlreadyExistException("이미 있는 리뷰입니다.");
        }

        SellerReview review = SellerReview.builder()
                .seller(reviewer)
                .buyer(trade.getBuyer())
                .reviewTemplate(reviewDTO.getReviewTemplate())
                .trade(trade)
                .build();

        return sellerReviewRepository.save(review);
    }
}
