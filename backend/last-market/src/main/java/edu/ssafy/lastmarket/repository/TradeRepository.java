package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query("select t from Trade t left join fetch t.product left join fetch t.buyer where t.seller=:seller")
    List<Trade> findBySeller(@Param("seller") Member member, Pageable pageable);

    @Query(value = "select t from Trade t left join fetch t.product where t.buyer =:buyer")
    List<Trade> findByBuyer(@Param("buyer") Member member, Pageable pageable);

    boolean existsByProductId(Long productId);
}
