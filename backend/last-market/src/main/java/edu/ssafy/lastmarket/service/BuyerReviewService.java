package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ReviewDTO;
import edu.ssafy.lastmarket.domain.entity.BuyerReview;
import edu.ssafy.lastmarket.domain.entity.Member;

public interface BuyerReviewService {
    BuyerReview saveBuyerReview(Member reviewer, ReviewDTO reviewDTO);
}
