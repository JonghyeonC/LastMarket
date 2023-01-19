package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.MemberRegistDto;
import edu.ssafy.lastmarket.domain.eneity.Member;

public interface MemberService {

    Member updateMember(MemberRegistDto memberRegistDto, String username);

    Member updateProfile(String imgUrl, String username);

}
