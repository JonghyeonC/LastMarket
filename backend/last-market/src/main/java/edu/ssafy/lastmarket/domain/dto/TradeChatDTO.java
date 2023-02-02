package edu.ssafy.lastmarket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TradeChatDTO {
    private String sender;
    private String msg;
    private LocalDateTime sendDate;
}
