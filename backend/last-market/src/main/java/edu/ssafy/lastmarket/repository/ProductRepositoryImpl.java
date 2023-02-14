package edu.ssafy.lastmarket.repository;

import com.mongodb.internal.operation.DeleteOperation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import edu.ssafy.lastmarket.domain.entity.*;
import edu.ssafy.lastmarket.repository.querydslutil.QueryDslUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static edu.ssafy.lastmarket.domain.entity.QLocation.location;
import static edu.ssafy.lastmarket.domain.entity.QMember.member;
import static edu.ssafy.lastmarket.domain.entity.QProduct.product;
import static edu.ssafy.lastmarket.domain.entity.QFavorite.favorite;
import static org.springframework.util.ObjectUtils.isEmpty;

public class ProductRepositoryImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }



    @Override
    public Page<Product> getProductList(Optional<Location> locationOptional,
                                        Optional<Category> categoryOptional,
                                        List<DealState> dealStates,
                                        Lifestyle lifestyleOptional,
                                        String keyword,
                                        Pageable pageable) {

        List<OrderSpecifier> ORDERS = getAllOrderSpecifiers(pageable);

        List<Product> productList = queryFactory
                .selectFrom(product)
                .where(
                        isLocation(locationOptional),
                        isCategory(categoryOptional),
                        isDealState(dealStates),
                        isLifestyle(lifestyleOptional),
                        isContaionKeyword(keyword)
                )
                .leftJoin(product.seller, member)
                .fetchJoin()
                .leftJoin(product.location, location)
                .fetchJoin()
                .orderBy(ORDERS.stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        PageImpl<Product> result = new PageImpl<>(productList,pageable, productList.size());
        return result;
    }



    private BooleanExpression isLocation(Optional<Location> locationOptional) {
        return locationOptional.isEmpty()? null : product.location.eq(locationOptional.get());
    }

    private BooleanExpression isCategory(Optional<Category> categoryOptional){
        return categoryOptional.isEmpty()?null: product.category.eq(categoryOptional.get());
    }

    private BooleanBuilder isDealState(List<DealState> dealStates){

        BooleanBuilder builder = new BooleanBuilder();
        if(dealStates==null|| dealStates.size()==0){
            return null;
        }else{
            for (DealState dealState : dealStates) {
                builder.or(product.dealState.eq(dealState));
            }
            return builder;
        }
    }


    private BooleanExpression isLifestyle(Lifestyle lifestyle){
        if(lifestyle == null){
            return null;
        }else{
            return product.lifestyle.eq(lifestyle);
        }
    }

    private BooleanExpression isContaionKeyword(String keyword){
        if(StringUtil.isNullOrEmpty(keyword)){
            return null;
        }else{
            return (product.title.contains(keyword)).or(product.content.contains(keyword));
        }
    }



    private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {

        List<OrderSpecifier> ORDERS = new ArrayList<>();

        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "favoriteCnt":
                        OrderSpecifier<?> orderFavoriteCnt = QueryDslUtil.getSortedColumn(direction, product.favoriteCnt, "favoriteCnt");
                        ORDERS.add(orderFavoriteCnt);
                        break;
                    case "createdDateTime":
                        OrderSpecifier<?> orderCreatedDateTime = QueryDslUtil.getSortedColumn(direction, product.createdDateTime, "createdDateTime");
                        ORDERS.add(orderCreatedDateTime);
                        break;
                    case "lastModifiedDateTime":
                        OrderSpecifier<?> orderLastModifiedDateTime = QueryDslUtil.getSortedColumn(direction, product.lastModifiedDateTime, "lastModifiedDateTime");
                        ORDERS.add(orderLastModifiedDateTime);
                        break;
                    case "startingPrice":
                        OrderSpecifier<?> orderStartingPrice = QueryDslUtil.getSortedColumn(direction, product.startingPrice, "startingPrice");
                        ORDERS.add(orderStartingPrice);
                        break;
                    case "instantPrice":
                        OrderSpecifier<?> orderInstantPrice = QueryDslUtil.getSortedColumn(direction, product.instantPrice, "instantPrice");
                        ORDERS.add(orderInstantPrice);
                        break;
                    default:
                        break;
                }
            }
        }

        return ORDERS;
    }
}
