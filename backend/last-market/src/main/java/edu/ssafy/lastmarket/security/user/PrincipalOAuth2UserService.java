package edu.ssafy.lastmarket.security.user;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.repository.MemberRepository;
import edu.ssafy.lastmarket.security.provider.KakaoOAuthUserInfo;
import edu.ssafy.lastmarket.security.provider.NaverOAuthUserInfo;
import edu.ssafy.lastmarket.security.provider.OAuthUserInfo;
import edu.ssafy.lastmarket.security.user.OAuth2UserImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = getOAuth2User(userRequest);

        OAuthUserInfo oAuthUserInfo = null;

        String provider = userRequest.getClientRegistration().getRegistrationId();

        if (provider.equals("naver")) {
            oAuthUserInfo = new NaverOAuthUserInfo(oAuth2User.getAttribute("response"));
        } else if(provider.equals("kakao")){
            oAuthUserInfo= new KakaoOAuthUserInfo(oAuth2User.getAttributes());
        }
        String providerId = oAuthUserInfo != null ? oAuthUserInfo.getProviderId() : "";
        String username = provider + "_" + providerId;

        Optional<Member> findUser = memberRepository.findByUsername(username);

        Member member;

        if (findUser.isEmpty()) {
            member = new Member(username);
            memberRepository.save(member);
        } else {
            member = findUser.get();
        }

        return new OAuth2UserImpl(member, oAuthUserInfo);

    }

    public OAuth2User getOAuth2User(OAuth2UserRequest userRequest) {
        return super.loadUser(userRequest);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        if (memberOptional.isEmpty()) {
            throw new UsernameNotFoundException("this token's owner username not found");

        }
        Member member = memberOptional.orElse(null);
        return new OAuth2UserImpl(member);

    }
}

