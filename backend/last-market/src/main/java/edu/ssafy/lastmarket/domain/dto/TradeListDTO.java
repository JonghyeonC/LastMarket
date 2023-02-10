package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.entity.Trade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeListDTO {
    Long tradeId;
    Long productId;
    String title;

    Long sellerId;
    String sellerNickname;
    Long buyerId;
    String buyerNickname;
    LocalDateTime createdDateTime;
    String imgURI;

    public TradeListDTO(Trade trade){
        this.tradeId = trade.getId();
        this.productId = trade.getProduct().getId();
        this.title = trade.getProduct().getTitle();
        this.sellerId = trade.getSeller().getId();
        this.sellerNickname = trade.getSeller().getNickname();
        this.buyerId = trade.getBuyer().getId();
        this.buyerNickname = trade.getBuyer().getNickname();
        this.createdDateTime = trade.getCreatedDateTime();
        this.imgURI = (trade.getProduct().getImages()==null)
                ?null:trade.getProduct().getImages().get(0).getImage().getImageURL();


    }

}
