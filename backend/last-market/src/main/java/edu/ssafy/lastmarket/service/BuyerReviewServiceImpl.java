package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ReviewPostDTO;
import edu.ssafy.lastmarket.domain.entity.BuyerReview;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Trade;
import edu.ssafy.lastmarket.exception.ReviewAlreadyExistException;
import edu.ssafy.lastmarket.repository.BuyerReviewRepository;
import edu.ssafy.lastmarket.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyerReviewServiceImpl implements BuyerReviewService {
    private final BuyerReviewRepository buyerReviewRepository;
    private final TradeRepository tradeRepository;

    @Override
    @Transactional
    public BuyerReview saveBuyerReview(Member reviewer, ReviewPostDTO reviewPostDTO) {
        Trade trade = tradeRepository.findById(reviewPostDTO.getTradeId())
                .orElseThrow(() -> new IllegalArgumentException("없는 거래입니다."));

        boolean exist = buyerReviewRepository.existsByBuyerAndTrade(reviewer, trade);
        if (exist) {
            throw new ReviewAlreadyExistException("이미 있는 리뷰입니다.");
        }

        BuyerReview review = BuyerReview.builder()
                .seller(trade.getSeller())
                .buyer(reviewer)
                .reviewTemplate(reviewPostDTO.getReviewTemplate())
                .trade(trade)
                .build();

        return buyerReviewRepository.save(review);
    }

    @Override
    public List<BuyerReview> getBuyerReviewList(Member buyer) {
        return buyerReviewRepository.findAllByBuyer(buyer);
    }
}
