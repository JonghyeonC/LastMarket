package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.TestUtils;
import edu.ssafy.lastmarket.domain.dto.ReviewPostDTO;
import edu.ssafy.lastmarket.domain.entity.*;
import edu.ssafy.lastmarket.exception.ReviewAlreadyExistException;
import edu.ssafy.lastmarket.repository.SellerReviewRepository;
import edu.ssafy.lastmarket.repository.TradeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellerReviewServiceImplTest {

    public SellerReviewService sellerReviewService;
    @Mock
    SellerReviewRepository sellerReviewRepository;
    @Mock
    TradeRepository tradeRepository;

    @Test
    @DisplayName("리뷰 저장 테스트")
    void saveReview() {
        //given
        Trade trade = TestUtils.getTrade();
        Member seller = getSeller();
        SellerReview sellerReview = getSellerReview(trade, seller);

        doReturn(Optional.of(trade)).when(tradeRepository).findById(1L);
        when(sellerReviewRepository.existsBySellerAndTrade(seller, trade)).thenReturn(false);
        //안에서 생성되는 객체랑 sellerReview가 달라져서 any로 받아야된다.
        when(sellerReviewRepository.save(any())).thenReturn(sellerReview);

        sellerReviewService = new SellerReviewServiceImpl(sellerReviewRepository, tradeRepository);
        //when
        ReviewPostDTO reviewPostDTO = ReviewPostDTO.builder()
                .tradeId(1L)
                .reviewTemplate(ReviewTemplate.GOOD)
                .build();
        SellerReview result = sellerReviewService.saveSellerReview(seller, reviewPostDTO);

        //then
        assertThat(result).isEqualTo(sellerReview);
    }

    @Test
    @DisplayName("이미 있는 리뷰")
    void AlreadyExistReview() {
        //given
        Trade trade = TestUtils.getTrade();
        Member seller = getSeller();

        doReturn(Optional.of(trade)).when(tradeRepository).findById(1L);
        when(sellerReviewRepository.existsBySellerAndTrade(seller, trade)).thenReturn(true);

        sellerReviewService = new SellerReviewServiceImpl(sellerReviewRepository, tradeRepository);
        //when
        ReviewPostDTO reviewPostDTO = ReviewPostDTO.builder()
                .tradeId(1L)
                .reviewTemplate(ReviewTemplate.GOOD)
                .build();

        assertThatThrownBy(() -> sellerReviewService.saveSellerReview(seller, reviewPostDTO))
                .isInstanceOf(ReviewAlreadyExistException.class);
    }

    private static Member getSeller() {
        return Member.builder()
                .id(1L)
                .nickname("seller")
                .profile(new Image("imageName", "imageURL"))
                .lifestyle(Lifestyle.MINIMAL)
                .username("seller")
                .location(TestUtils.getLocation())
                .build();
    }

    private static SellerReview getSellerReview(Trade trade, Member seller) {
        return SellerReview.builder()
                .id(1L)
                .trade(trade)
                .reviewTemplate(ReviewTemplate.GOOD)
                .buyer(trade.getBuyer())
                .seller(seller)
                .build();
    }

}