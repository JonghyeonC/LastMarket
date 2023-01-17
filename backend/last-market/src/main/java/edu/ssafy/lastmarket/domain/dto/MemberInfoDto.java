package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.eneity.Favorite;
import edu.ssafy.lastmarket.domain.eneity.Image;
import edu.ssafy.lastmarket.domain.eneity.Location;
import edu.ssafy.lastmarket.domain.eneity.Member;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class MemberInfoDto {

    private Long id;
    private String username;
    private String nickname;
    private String profile;
    private String location;

    public MemberInfoDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.profile = (member.getProfile() == null) ? "" : member.getProfile().getImageURL();
        this.location = (member.getLocation() == null) ? "" : member.getLocation().toString();
    }

}
