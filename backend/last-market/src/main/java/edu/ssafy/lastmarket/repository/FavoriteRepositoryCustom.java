package edu.ssafy.lastmarket.repository;

import edu.ssafy.lastmarket.domain.entity.Favorite;
import edu.ssafy.lastmarket.domain.entity.Member;

import java.util.List;

public interface FavoriteRepositoryCustom {
    List<Favorite> findByMemberAndProdictIdIn(Member member, List<Long> productId);
}
