package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.MemberRegistDto;
import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.Location;
import edu.ssafy.lastmarket.domain.entity.Member;
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
        if(!StringUtil.isNullOrEmpty(memberRegistDto.getNickname())){
            member.setNickname(memberRegistDto.getNickname());
        }
        if(!Objects.isNull(memberRegistDto.getJob())){
            member.setJob(memberRegistDto.getJob());
        }
        if(!Objects.isNull(memberRegistDto.getCategories())){
            Optional<Location> location= locationService.findDongCodeByAddress(memberRegistDto.getAddr());
            member.setLocation(location.orElseGet(()->null));
        }

        return member;

    }

    @Override
    public Member updateProfile(Optional<Image> imageOptional, Member member) {


        imageOptional.ifPresent(member::setProfile);

        return member;

    }
}
