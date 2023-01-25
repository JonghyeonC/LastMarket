package edu.ssafy.lastmarket.security;

import com.google.gson.Gson;
import edu.ssafy.lastmarket.domain.dto.MemberInfoDto;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.repository.MemberRepository;
import edu.ssafy.lastmarket.security.user.OAuth2UserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final Gson gson;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2UserImpl oAuth2User =  (OAuth2UserImpl) authentication.getPrincipal();
        Optional<Member> memberOptional = memberRepository.findByUsername(oAuth2User.getUsername());




        if(Objects.isNull(memberOptional.get().getNickname())){
            response.setStatus(201);
        }else{
            response.setStatus(200);

            PrintWriter writer = response.getWriter();
            writer.print(gson.toJson(new MemberInfoDto(memberOptional.get())));

        }
//        RequestDispatcher rd = request.getRequestDispatcher("/oauth/redirect");
//        rd.forward(request, response);


    }

    private void writeTokenResponse(HttpServletResponse response, String token) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Authorization", token);

    }
}
