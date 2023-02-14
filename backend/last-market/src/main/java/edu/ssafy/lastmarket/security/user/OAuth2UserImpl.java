package edu.ssafy.lastmarket.security.user;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.security.provider.OAuthUserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;


public class OAuth2UserImpl implements OAuth2User, UserDetails {
    private Member member;
    private OAuthUserInfo oAuthUserInfo;

    public OAuth2UserImpl(Member member) {
        this.member = member;
    }

    public OAuth2UserImpl(Member member, OAuthUserInfo oAuthUserInfo) {
        this.member = member;
        this.oAuthUserInfo = oAuthUserInfo;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuthUserInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole().getDescription()));
        return roles;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getName() {
        return oAuthUserInfo.getProvider() + "_" + oAuthUserInfo.getProviderId();
    }

    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Member getMember(){
        return this.member;
    }

}
