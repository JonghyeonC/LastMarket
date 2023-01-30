package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.Trade;
import edu.ssafy.lastmarket.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Override
    public Trade saveTrade(Product product, Member seller, Member buyer) {

        Trade trade = new Trade(product, seller, buyer);
        return tradeRepository.save(trade);
    }
}
