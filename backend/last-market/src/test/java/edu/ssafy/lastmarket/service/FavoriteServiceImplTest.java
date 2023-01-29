package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.TestUtils;
import edu.ssafy.lastmarket.domain.entity.Favorite;
import edu.ssafy.lastmarket.repository.FavoriteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceImplTest {


    @Mock
    FavoriteRepository favoriteRepository;

    FavoriteServiceImpl favoriteService;


    @Test
    @DisplayName("favorite true test")
    public void isFavoriteTest(){

        Favorite favorite = TestUtils.getFavorite();
        doReturn(Optional.of(favorite)).when(favoriteRepository).findByMemberAndProductId(any(),any());

        favoriteService= new FavoriteServiceImpl(favoriteRepository);

        Boolean isFavoriteChecked = favoriteService.isFavoriteChecked(TestUtils.getMember(),1L);

        assertThat(true).isEqualTo(isFavoriteChecked);

    }

    @Test
    @DisplayName("favorite false test")
    public void isFavoriteFalseTest(){

        Favorite favorite = TestUtils.getFavorite();
        doReturn(Optional.of(favorite)).when(favoriteRepository).findByMemberAndProductId(any(),any());

        favoriteService= new FavoriteServiceImpl(favoriteRepository);

        Boolean isFavoriteChecked = favoriteService.isFavoriteChecked(TestUtils.getMember(),0L);

        assertThat(false).isEqualTo(isFavoriteChecked);

    }



}
