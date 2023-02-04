package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.entity.BuyerReview;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerReviewRepository extends JpaRepository<BuyerReview, Long> {
    BuyerReview save(BuyerReview review);

    boolean existsByBuyerAndTrade(Member buyer, Trade trade);
}
