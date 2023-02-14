package edu.ssafy.lastmarket.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ChatListDTO {
    private Long otherId;
    private String otherName;
    private String otherImageUrl;
    private Long productId;
    private String roomId;
    private TradeChatDTO lastChat;
}
