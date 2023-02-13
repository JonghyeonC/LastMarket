package edu.ssafy.lastmarket.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TradeChatDTO {
    private Long sender;
    private String msg;
    private LocalDateTime sendDate;
}
