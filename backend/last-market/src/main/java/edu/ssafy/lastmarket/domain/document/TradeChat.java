package edu.ssafy.lastmarket.domain.document;

import edu.ssafy.lastmarket.domain.dto.TradeChatDTO;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "lastmarket")
@Getter
@ToString
public class TradeChat {
    @Id
    private String id;
    private List<TradeChatDTO> chatLogs;

    public TradeChat(String id) {
        this.id = id;
        this.chatLogs = new ArrayList<>();
    }
}
