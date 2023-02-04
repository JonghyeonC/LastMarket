package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.entity.ReviewTemplate;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPostDTO {
    private Long tradeId;
    private ReviewTemplate reviewTemplate;
}
