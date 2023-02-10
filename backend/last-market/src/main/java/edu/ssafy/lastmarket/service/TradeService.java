package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.TradeListDTO;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TradeService {
    Trade saveTrade(Product product, Member buyer);

    boolean isSeller(Long tradeId, Member member);
    boolean isBuyer(Long tradeId, Member member);

    List<TradeListDTO> getTradeBySeller(Member member, Pageable pageable);
    List<TradeListDTO> getTradeByBuyer(Member member, Pageable pageable);

}
