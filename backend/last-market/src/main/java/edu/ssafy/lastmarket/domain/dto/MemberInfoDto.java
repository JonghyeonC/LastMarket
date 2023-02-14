package edu.ssafy.lastmarket.domain.dto;

import edu.ssafy.lastmarket.domain.entity.Lifestyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    private String nickname;
    private String profile;
    private Lifestyle lifestyles;
    private String addr;
}
