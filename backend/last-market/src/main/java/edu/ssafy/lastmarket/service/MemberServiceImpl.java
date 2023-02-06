package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.MemberInfoDto;
import edu.ssafy.lastmarket.domain.dto.MemberRegistDto;
import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.Location;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.exception.NotFoundException;
import edu.ssafy.lastmarket.repository.MemberRepository;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LocationService locationService;

    @Override
    @Transactional
    public Member updateMember(MemberRegistDto memberRegistDto, Member member) {

        memberRepository.save(member);
        if (!StringUtil.isNullOrEmpty(memberRegistDto.getNickname())) {
            member.setNickname(memberRegistDto.getNickname());
        }
        if (!Objects.isNull(memberRegistDto.getLifestyle())) {
            member.setLifestyle(memberRegistDto.getLifestyle());
        }
        if (!Objects.isNull(memberRegistDto.getCategories())) {
            Optional<Location> location = locationService.findDongCodeByAddress(memberRegistDto.getAddr());
            member.setLocation(location.orElseGet(() -> null));
        }

        return member;

    }

    @Override
    @Transactional
    public Member updateProfile(Optional<Image> imageOptional, Member member) {
        imageOptional.ifPresent(member::setProfile);

        return member;
    }

    @Override
    public MemberInfoDto getMemberInfo(Member member) {
        return MemberInfoDto.builder()
                .nickname(member.getNickname())
                .profile(member.getProfile().getImageURL())
                .lifestyles(member.getLifestyle())
                .addr(member.getLocation().toString())
                .build();
    }

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(()->new NotFoundException("없는 유저입니다."));
    }
}
