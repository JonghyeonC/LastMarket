package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.Trade;

import java.util.Optional;

public interface TradeService {
    Trade saveTrade(Product product, Member buyer);

    boolean isSeller(Long tradeId, Member member);
    boolean isBuyer(Long tradeId, Member member);
}
