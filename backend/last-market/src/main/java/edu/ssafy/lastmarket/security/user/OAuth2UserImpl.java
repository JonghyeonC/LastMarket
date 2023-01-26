package edu.ssafy.lastmarket.security.user;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.security.provider.OAuthUserInfo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class OAuth2UserImpl implements OAuth2User, UserDetails {

    public OAuth2UserImpl(Member member){
       this.member = member;
    }

    public OAuth2UserImpl(Member member, OAuthUserInfo oAuthUserInfo){
        this.member = member;
        this.oAuthUserInfo = oAuthUserInfo;
    }

    private Member member;
    private OAuthUserInfo oAuthUserInfo;

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
        return oAuthUserInfo.getProvider()+"_"+ oAuthUserInfo.getProviderId();
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

}
