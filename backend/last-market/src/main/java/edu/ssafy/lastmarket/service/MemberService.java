package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.dto.MemberRegistDto;
import edu.ssafy.lastmarket.domain.eneity.Image;
import edu.ssafy.lastmarket.domain.eneity.Member;

import java.util.Optional;

public interface MemberService {

    Member updateMember(MemberRegistDto memberRegistDto, String username);

    Member updateProfile(Optional<Image> imageOptional, String username);

}
