package edu.ssafy.lastmarket.security;

import com.google.gson.Gson;
import edu.ssafy.lastmarket.domain.dto.MemberInfoDto;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.jwt.JwtManager;
import edu.ssafy.lastmarket.repository.MemberRepository;
import edu.ssafy.lastmarket.security.user.OAuth2UserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final Gson gson;

    private final JwtManager jwtManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2UserImpl oAuth2User =  (OAuth2UserImpl) authentication.getPrincipal();

//        System.out.println(oAuth2User.getName()+" "+oAuth2User.getUsername());
        Optional<Member> memberOptional = memberRepository.findMemberFetchJoinByUsername(oAuth2User.getUsername());

//        System.out.println(((OAuth2UserImpl) authentication.getPrincipal()).getName());
//        System.out.println("member username "+memberOptional.get().getUsername());

        Member member = memberOptional.get();
//        System.out.println(member.getLocation().toString());
//        member.getProducts();


        MemberInfoDto memberInfoDto = new MemberInfoDto(member);
        String shortToken = jwtManager.generateJwtToken(member);
        String longToken = jwtManager.generateRefreshJwtToken(member);
//			System.out.println(token);

//        System.out.println(memberOptional.get().getNickname());
        if(memberInfoDto.getNickname().equals("")){
            response.setStatus(201);

        }else{
            response.setStatus(200);
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.addCookie(new Cookie("Authentication", shortToken));
        response.getWriter().print(gson.toJson(memberInfoDto));
        response.addHeader("Authorization", shortToken);
//        RequestDispatcher rd = request.getRequestDispatcher("/oauth/redirect");
//        rd.forward(request, response);


    }

//    private void writeTokenResponse(HttpServletResponse response, String token) throws IOException {
//        response.setContentType("application/json;charset=UTF-8");
//        response.addHeader("Authorization", token);
//
//    }
}
