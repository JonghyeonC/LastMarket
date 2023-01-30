package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.*;
import edu.ssafy.lastmarket.exception.BanExistException;
import edu.ssafy.lastmarket.repository.BanRepository;
import edu.ssafy.lastmarket.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BanServiceImplTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    BanRepository banRepository;
    BanServiceImpl banService;

    @Test
    @DisplayName("벤 추가")
    void addBan() {
        //given
        Member member1 = makeUser("user1", "nick2");
        Member member2 = makeUser("user2", "nick2");
        Ban ban = Ban.builder()
                .from(member1)
                .to(member2)
                .build();

        //테스트 시 banUser 안에서 새로운 ban객체가 만들어지는데 해당 객체가
        //mocking할 떄 객체랑 다른 객체로 인식되기 때문에 오류가 난다.
        doReturn(ban).when(banRepository).save(any(Ban.class));
        doReturn(Optional.empty()).when(banRepository).findByFromAndTo(member1, member2);
        doReturn(Optional.of(member2)).when(memberRepository).findByUsername("user2");

        banService = new BanServiceImpl(banRepository, memberRepository);
        //when
        Ban banSaved = banService.banUser(member1, "user2");
        //then
        assertThat(banSaved).isEqualTo(ban);
    }

    @Test
    @DisplayName("이미 벤한 경우")
    void alreadyBan() {
        //given
        Member member1 = makeUser("user1", "nick2");
        Member member2 = makeUser("user2", "nick2");
        Ban ban = Ban.builder()
                .from(member1)
                .to(member2)
                .build();

        //테스트 시 banUser 안에서 새로운 ban객체가 만들어지는데 해당 객체가
        //mocking할 떄 객체랑 다른 객체로 인식되기 때문에 오류가 난다.
        doReturn(Optional.of(ban)).when(banRepository).findByFromAndTo(member1, member2);
        doReturn(Optional.of(member2)).when(memberRepository).findByUsername("member2");

        banService = new BanServiceImpl(banRepository, memberRepository);
        //when
        assertThatThrownBy(() -> {
            Ban banSaved = banService.banUser(member1, "member2");
        }).isInstanceOf(BanExistException.class);
    }

    @Test
    @DisplayName("벤 리스트 찾기")
    void findBanList() {
        //given
        Member member1 = makeUser("user1", "nick2");
        Member member2 = makeUser("user2", "nick2");
        Member member3 = makeUser("user3", "nick3");
        Ban ban1 = Ban.builder()
                .from(member1)
                .to(member2)
                .build();
        Ban ban2 = Ban.builder()
                .from(member1)
                .to(member3)
                .build();

        //테스트 시 banUser 안에서 새로운 ban객체가 만들어지는데 해당 객체가
        //mocking할 떄 객체랑 다른 객체로 인식되기 때문에 오류가 난다.
        doReturn(List.of(ban1, ban2)).when(banRepository).findByFrom(member1);
//        doReturn(Optional.of(member2)).when(memberRepository).findByUsername("user2");

        banService = new BanServiceImpl(banRepository, memberRepository);
        List<Ban> banList = banService.findBanList(member1);
        //when
        assertThat(List.of(ban1, ban2)).isEqualTo(banList);
    }

    private Member makeUser(String username, String nickname) {
        Location location = Location.builder()
                .sido("서울시")
                .gugun("용산구")
                .dong("도원동")
                .dongCode("1117012000")
                .build();
        return Member.builder()
                .username(username)
                .nickname(nickname)
                .lifestyle(Lifestyle.MINIMAL)
                .banList(new ArrayList<>())
                .favorites(new ArrayList<>())
                .products(new ArrayList<>())
                .location(location)
                .role(Role.USER)
                .sellerReviews(new ArrayList<>())
                .buyerReviews(new ArrayList<>())
                .profile(new Image("name", "url"))
                .build();
    }
}