package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.entity.Favorite;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FavoriteService {

    boolean isFavoriteChecked(Member member, Long productId);
    Page<ProductListDto> isFavoriteChecked(Member member, Page<ProductListDto> productListDtoPage);
    List<ProductListDto> getFavorites(Member member);
    Favorite saveFavorite(Member member, Optional<Product> productOptional);
    void deleteFavorite(Member member, Optional<Product> productOptional);
}
