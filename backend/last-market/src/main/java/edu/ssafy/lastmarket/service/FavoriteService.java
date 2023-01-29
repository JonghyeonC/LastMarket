package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;

public interface FavoriteService {

    boolean isFavoriteChecked(Member member, Long productId);

}
