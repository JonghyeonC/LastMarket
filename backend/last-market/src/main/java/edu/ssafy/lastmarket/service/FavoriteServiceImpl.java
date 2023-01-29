package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.ProductListDto;
import edu.ssafy.lastmarket.domain.entity.Favorite;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService{

    private final FavoriteRepository favoriteRepository;

    /**
     *
     * @param member
     * @param productId
     * @return
     */
    @Override
    public boolean isFavoriteChecked(Member member, Long productId) {
        Optional<Favorite> favoriteOptional = favoriteRepository.findByMemberAndProductId(member,productId);
        return favoriteOptional.isEmpty()? false: true;

//        return favoriteRepository.existsByMemberAndProductId(member, productId);
    }

    @Override
    public Page<ProductListDto> isFavoriteChecked(Member member, Page<ProductListDto> productListDtoPage) {

        List<ProductListDto> productListDtoList = productListDtoPage.getContent();




        Page<ProductListDto> result = new PageImpl<>(
                productListDtoPage.getContent(),
                productListDtoPage.getPageable(),
                productListDtoPage.getTotalPages());

        return result;
    }
}
