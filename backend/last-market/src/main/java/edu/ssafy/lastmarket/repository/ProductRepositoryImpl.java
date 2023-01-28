package edu.ssafy.lastmarket.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import edu.ssafy.lastmarket.domain.entity.Category;
import edu.ssafy.lastmarket.domain.entity.DealState;
import edu.ssafy.lastmarket.domain.entity.Location;
import edu.ssafy.lastmarket.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static edu.ssafy.lastmarket.domain.entity.QLocation.location;
import static edu.ssafy.lastmarket.domain.entity.QMember.member;
import static edu.ssafy.lastmarket.domain.entity.QProduct.product;

public class ProductRepositoryImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }



    @Override
    public List<Product> getProductList(Optional<Location> locationOptional, Optional<Category> categoryOptional, DealState dealState, Pageable pageable) {
        List<Product> productList = queryFactory
                .selectFrom(product)
                .where(
                        isLocation(locationOptional),
                        isCategory(categoryOptional),
                        product.dealState.eq(dealState)
                )
                .leftJoin(product.seller, member)
                .fetchJoin()
                .leftJoin(product.location, location)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return productList;

    }



    private BooleanExpression isLocation(Optional<Location> locationOptional) {
        return locationOptional.isEmpty()? null : product.location.eq(locationOptional.get());
    }

    private BooleanExpression isCategory(Optional<Category> categoryOptional){
        return categoryOptional.isEmpty()?null: product.category.eq(categoryOptional.get());
    }

}
