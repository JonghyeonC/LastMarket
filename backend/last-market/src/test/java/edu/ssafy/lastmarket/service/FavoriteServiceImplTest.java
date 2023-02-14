package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.TestUtils;
import edu.ssafy.lastmarket.domain.entity.Favorite;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Product;
import edu.ssafy.lastmarket.repository.FavoriteRepository;
import edu.ssafy.lastmarket.repository.ProductRepository;
import edu.ssafy.lastmarket.service.impl.FavoriteServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceImplTest {


    @Mock
    FavoriteRepository favoriteRepository;

    @Mock
    ProductRepository productRepository;

    FavoriteServiceImpl favoriteService;


    @Test
    @DisplayName("favorite true test")
    public void isFavoriteTest(){
        Member member = TestUtils.getMember();
        Product product = TestUtils.getProduct();
        Favorite favorite = new Favorite(1L,member, product );
        doReturn(Optional.ofNullable(favorite)).when(favoriteRepository).findByMemberAndProductId(any(),any());

        favoriteService= new FavoriteServiceImpl(favoriteRepository);

        Boolean isFavoriteChecked = favoriteService.isFavoriteChecked(member,1L);
        assertThat(isFavoriteChecked).isTrue();

    }


    @Test
    @DisplayName("post favorite")
    public void saveFavorite(){
        Member member = TestUtils.getMember();
        Product product = TestUtils.getProduct();
        Favorite favorite = new Favorite(member, product);
        favorite.setId(1L);


        lenient(). doReturn(Optional.ofNullable(favorite)).when(favoriteRepository).findByMemberAndProductId(any(),any());
        lenient().doReturn(favorite).when(favoriteRepository).save(any());
        lenient().doReturn(Optional.ofNullable(product)).when(productRepository).findProductMemberById(any());

        favoriteService= new FavoriteServiceImpl(favoriteRepository);

        Favorite result = favoriteService.saveFavorite(member, Optional.of(product));
        assertThat(result).isEqualTo(favorite);
    }




}
