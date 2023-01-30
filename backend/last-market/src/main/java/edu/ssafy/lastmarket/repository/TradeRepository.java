package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
}
