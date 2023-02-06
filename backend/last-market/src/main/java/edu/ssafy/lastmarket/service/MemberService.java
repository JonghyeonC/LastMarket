package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.MemberInfoDto;
import edu.ssafy.lastmarket.domain.dto.MemberRegistDto;
import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.Member;

import java.util.Optional;

public interface MemberService {

    Member updateMember(MemberRegistDto memberRegistDto, Member member);

    Member updateProfile(Optional<Image> imageOptional, Member member);

    MemberInfoDto getMemberInfo(Member member);

    Member findMemberById(Long id);
}
