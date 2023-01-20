package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.MemberRegistDto;
import edu.ssafy.lastmarket.domain.eneity.Image;
import edu.ssafy.lastmarket.domain.eneity.Location;
import edu.ssafy.lastmarket.domain.eneity.Member;
import edu.ssafy.lastmarket.exception.NotMemberUsernameException;
import edu.ssafy.lastmarket.repository.LocationRepository;
import edu.ssafy.lastmarket.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository;
    private final LocationService locationService;
    @Override
    @Transactional
    public Member updateMember(MemberRegistDto memberRegistDto, String username) {

        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        if(memberOptional.isEmpty()){
            throw new NotMemberUsernameException();
        }
        Member member = memberOptional.get();

        if(!Objects.isNull(memberRegistDto.getNickname())){
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
    public Member updateProfile(Optional<Image> imageOptional, String username) {
        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        if(memberOptional.isEmpty()){
            throw new NotMemberUsernameException();
        }

        Member member = memberOptional.get();
        imageOptional.ifPresent(member::setProfile);

        return member;

    }
}
