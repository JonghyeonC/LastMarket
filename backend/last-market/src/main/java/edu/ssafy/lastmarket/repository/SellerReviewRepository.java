package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.SellerReview;
import edu.ssafy.lastmarket.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerReviewRepository extends JpaRepository<SellerReview, Long> {
    SellerReview save(SellerReview review);

    boolean existsBySellerAndTrade(Member seller, Trade trade);

    List<SellerReview> findAllBySeller(Member seller);
}
