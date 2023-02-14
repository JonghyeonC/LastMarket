package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ReviewPostDTO;
import edu.ssafy.lastmarket.domain.entity.BuyerReview;
import edu.ssafy.lastmarket.domain.entity.Member;

import java.util.List;

public interface BuyerReviewService {
    BuyerReview saveBuyerReview(Member reviewer, ReviewPostDTO reviewPostDTO);
    List<BuyerReview> getBuyerReviewList(Member buyer);
}
