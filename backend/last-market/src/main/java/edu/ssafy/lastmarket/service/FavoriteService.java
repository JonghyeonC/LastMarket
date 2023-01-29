package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FavoriteService {

    boolean isFavoriteChecked(Member member, Long productId);
    Page<ProductListDto> isFavoriteChecked(Member member, Page<ProductListDto> productListDtoPage);
}
