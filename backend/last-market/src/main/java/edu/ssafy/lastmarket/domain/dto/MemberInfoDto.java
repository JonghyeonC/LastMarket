package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {

    private Long id;
    private String username;
    private String nickname;
    private String profile;
    private String location;

    public MemberInfoDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.nickname = (member.getNickname() == null)? "" : member.getNickname();
        this.profile = (Objects.isNull(member.getProfile())) ? "" : member.getProfile().getImageURL();
        this.location = (Objects.isNull(member.getLocation())) ? "" : member.getLocation().toString();
    }

}
