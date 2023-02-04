package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ReviewDTO;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.SellerReview;

public interface SellerReviewService {
    SellerReview saveSellerReview(Member reviewer, ReviewDTO reviewDTO);
}
