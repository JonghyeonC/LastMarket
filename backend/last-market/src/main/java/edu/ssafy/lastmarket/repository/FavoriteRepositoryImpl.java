package edu.ssafy.lastmarket.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import edu.ssafy.lastmarket.domain.entity.Favorite;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.domain.entity.QFavorite;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;
import java.util.List;

import static edu.ssafy.lastmarket.domain.entity.QFavorite.favorite;
import static edu.ssafy.lastmarket.domain.entity.QProduct.product;

public class FavoriteRepositoryImpl implements FavoriteRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public FavoriteRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Favorite> findByMemberAndProdictIdIn(Member member, List<Long> productId) {
//        List<Favorite> result = queryFactory
//                .selectFrom(favorite)
//                .where(
//                    favorite.member.getId().equals(member.getId()),
//                        favorite.product.id.in(productId)
//                )
//                .fetch();
        return null;
    }
}
