package edu.ssafy.lastmarket.domain.document;

import edu.ssafy.lastmarket.domain.dto.TradeChatDTO;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
public class TradeChat {
    @Id
    private String id;
    private List<TradeChatDTO> chatLogs;

    public TradeChat(Member seller, Member buyer, Product product, List<TradeChatDTO> chatLogs) {
        this.id = seller.getUsername() + buyer.getUsername() + product.getId();
        this.chatLogs = new ArrayList<>();
    }
}
