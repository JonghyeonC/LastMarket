package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ReviewPostDTO;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.SellerReview;

import java.util.List;

public interface SellerReviewService {
    SellerReview saveSellerReview(Member reviewer, ReviewPostDTO reviewPostDTO);
    List<SellerReview> getSellerReviewList(Member seller);
}
