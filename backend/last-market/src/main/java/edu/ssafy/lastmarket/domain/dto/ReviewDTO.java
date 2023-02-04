package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.entity.ReviewTemplate;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private Long buyerId;
    private String buyerNickname;
    private ReviewTemplate reviewTemplate;
}
