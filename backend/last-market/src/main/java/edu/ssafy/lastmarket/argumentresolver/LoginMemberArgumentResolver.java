package edu.ssafy.lastmarket.argumentresolver;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.jwt.JwtManager;
import edu.ssafy.lastmarket.repository.MemberRepository;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String token = "";
        if (!Objects.isNull(request.getCookies())) {
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("Authentication")) {
                    token = cookies[i].getValue();
                }
            }
        }
        if(!Objects.isNull(request.getHeader("Authentication"))){
            token = request.getHeader("Authentication");
        }

        if (StringUtil.isNullOrEmpty(token)) {
            throw new IllegalArgumentException("로그인 해주세요");
        }
        if(!jwtManager.isValidate(token)){
            throw new IllegalArgumentException("로그인 해주세요");
        }

        String username = jwtManager.getUsername(token);
        Optional<Member> memberOptional = memberRepository.findByUsername(username);

        return memberOptional.orElse(null);

    }
}
