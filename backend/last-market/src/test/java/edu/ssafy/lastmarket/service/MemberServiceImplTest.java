package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.TestUtils;
import edu.ssafy.lastmarket.domain.dto.MemberInfoDto;
import edu.ssafy.lastmarket.domain.dto.MemberRegistDto;
import edu.ssafy.lastmarket.domain.entity.Lifestyle;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.repository.MemberRepository;
import edu.ssafy.lastmarket.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    LocationService locationService;
    @InjectMocks
    MemberServiceImpl memberService;

    @Test
    @DisplayName("사용자 등록")
    public void locationSplit4() {

        Member member = Member.builder()
                .id(1L)
                .nickname("nick")
                .lifestyle(Lifestyle.MINIMAL)
                .username("asdf")
                .build();

        MemberRegistDto memberRegistDto = MemberRegistDto.builder()
                .nickname("nick2")
                .lifestyle(Lifestyle.FLEX)
                .build();

        Member predict = Member.builder()
                .id(1L)
                .nickname("nick2")
                .username("asdf")
                .lifestyle(Lifestyle.FLEX)
                .build();

        Optional<Member> memberOptional = Optional.of(member);

        lenient().doReturn(memberOptional).when(memberRepository).findByUsername(any());
        lenient().doReturn(null).when(locationService).findDongCodeByAddress(any());

        memberService = new MemberServiceImpl(memberRepository, locationService);
        Member result = memberService.updateMember(memberRegistDto, member);

        assertThat(predict.getNickname()).isEqualTo(result.getNickname());
        assertThat(predict.getLifestyle()).isEqualTo(result.getLifestyle());
    }

    @Test
    @DisplayName("유저 정보 찾아오기")
    void getMemberInfo() {
        //given
        Member member = TestUtils.getMember();
        memberService = new MemberServiceImpl(memberRepository, locationService);

        //when
        MemberInfoDto memberInfo = memberService.getMemberInfo(member);

        MemberInfoDto result = MemberInfoDto.builder()
                .nickname("nick")
                .lifestyles(Lifestyle.MINIMAL)
                .profile("imageURL")
                .addr("서울특별시 종로구 궁정동")
                .build();
        //then
        assertThat(memberInfo).isEqualTo(result);
    }
}
