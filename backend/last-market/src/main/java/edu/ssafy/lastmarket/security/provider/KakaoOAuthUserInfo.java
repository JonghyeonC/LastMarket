package edu.ssafy.lastmarket.security.provider;

import lombok.RequiredArgsConstructor;
import java.util.Map;

@RequiredArgsConstructor
public class KakaoOAuthUserInfo  implements OAuthUserInfo{
    private final Map<String, Object> attributes;

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
