package edu.ssafy.lastmarket.jwt;

import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.exception.NeedNicknameAndLocation;
import edu.ssafy.lastmarket.security.user.OAuth2UserImpl;
import edu.ssafy.lastmarket.security.user.PrincipalOAuth2UserService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final PrincipalOAuth2UserService principalOAuth2UserService;

    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Member member = new Member();
        String token = "";
        try {
            token = jwtManager.parseJwt(request);

            if (token != null && jwtManager.isValidate(token)) {

                String username = jwtManager.getUsername(token);

                UserDetails userDetails = principalOAuth2UserService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                OAuth2UserImpl oAuth2User = (OAuth2UserImpl) userDetails;
                member = oAuth2User.getMember();
                log.info("member: {}", member.getUsername());
                if (StringUtil.isNullOrEmpty(member.getNickname())|| Objects.isNull(member.getLocation())) {
                    log.info("====================================");
                    throw new NeedNicknameAndLocation("needNicknameAndLocation");
                }

                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

            }
        } catch (BadCredentialsException e) {
            loginFailure(request, response);

        }catch(NeedNicknameAndLocation e){

            request.setAttribute("exception","NeedNicknameAndLocation");
            request.setAttribute("token" , token);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }


    protected void loginFailure(HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, String> result = new HashMap<>();
            result.put("\"msg\"", "\"id or password missmatch\"");
            response.getWriter().print(result);
            response.setStatus(401);
            response.addHeader("msg", "id or password missmatch");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }




}

