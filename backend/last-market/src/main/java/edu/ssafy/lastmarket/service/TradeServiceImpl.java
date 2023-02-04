package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.DealState;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.Trade;
import edu.ssafy.lastmarket.exception.ProductSoldException;
import edu.ssafy.lastmarket.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Override
    @Transactional
    public Trade saveTrade(Product product, Member seller, Member buyer) {
        if (product.getDealState() == DealState.FINISH) {
            throw new ProductSoldException();
        }

        Trade trade = new Trade(product, seller, buyer);
        return tradeRepository.save(trade);
    }

    @Override
    public boolean isSeller(Long tradeId, Member member) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("없는 거래입니다."));

        return trade.getSeller().getId().equals(member.getId());
    }

    @Override
    public boolean isBuyer(Long tradeId, Member member) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("없는 거래입니다."));

        return trade.getBuyer().getId().equals(member.getId());
    }
}
