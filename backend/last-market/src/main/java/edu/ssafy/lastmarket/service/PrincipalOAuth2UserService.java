package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.eneity.Member;
import edu.ssafy.lastmarket.repository.MemberRepository;
import edu.ssafy.lastmarket.security.provider.KakaoOAuthUserInfo;
import edu.ssafy.lastmarket.security.provider.NaverOAuthUserInfo;
import edu.ssafy.lastmarket.security.provider.OAuthUserInfo;
import edu.ssafy.lastmarket.security.user.OAuth2UserImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = getOAuth2User(userRequest);

        OAuthUserInfo oAuthUserInfo = null;

        String provider = userRequest.getClientRegistration().getRegistrationId();

        if (provider.equals("naver")) {
            oAuthUserInfo = new NaverOAuthUserInfo(oAuth2User.getAttribute("response"));
        } else if(provider.equals("kakao")){
            System.out.println(oAuth2User);
            oAuthUserInfo= new KakaoOAuthUserInfo(oAuth2User.getAttributes());
        }
        String providerId = oAuthUserInfo != null ? oAuthUserInfo.getProviderId() : "";
        String username = provider + "_" + providerId;
        String email = oAuthUserInfo.getEmail();
        String name = oAuthUserInfo != null ? oAuthUserInfo.getName() : "";



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
}

