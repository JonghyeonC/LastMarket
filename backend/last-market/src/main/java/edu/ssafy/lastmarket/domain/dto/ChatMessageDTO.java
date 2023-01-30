package edu.ssafy.lastmarket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private ChatType chatType;
    private String roomKey;
    private String message;
}
