package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.MemberRegistDto;
import edu.ssafy.lastmarket.domain.entity.Lifestyle;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
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
                .lifestyle(Lifestyle.YOLO)
                .build();

        Member predict = Member.builder()
                .id(1L)
                .nickname("nick2")
                .username("asdf")
                .lifestyle(Lifestyle.YOLO)
                .build();

        Optional<Member> memberOptional = Optional.of(member);


        lenient().doReturn(memberOptional).when(memberRepository).findByUsername(any());
        lenient().doReturn(null).when(locationService).findDongCodeByAddress(any());


        memberService = new MemberServiceImpl(memberRepository, locationService);
        Member result = memberService.updateMember(memberRegistDto, member);



        assertThat(predict.getNickname()).isEqualTo(result.getNickname());
        assertThat(predict.getLifestyle()).isEqualTo(result.getLifestyle());
    }


}
