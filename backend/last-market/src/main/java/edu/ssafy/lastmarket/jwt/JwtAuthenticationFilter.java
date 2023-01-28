package edu.ssafy.lastmarket.jwt;

import edu.ssafy.lastmarket.security.user.OAuth2UserImpl;
import edu.ssafy.lastmarket.security.user.PrincipalOAuth2UserService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final PrincipalOAuth2UserService principalOAuth2UserService;

    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
//            System.out.println("=================\n===================");
//            ObjectMapper omo = new ObjectMapper();
//            System.out.println(omo.toString());
            String temp =request.getHeader("Authentication");

            if(StringUtil.isNullOrEmpty(temp)){
                filterChain.doFilter(request,response);
                return;
            }

            String username = jwtManager.getUsername(temp);

            UserDetails userDetails = principalOAuth2UserService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

//            System.out.println(authenticationToken.getPrincipal());
//            System.out.println(authenticationToken.getAuthorities());
            // PrincipalDetailsService의 loadUserByUsername() 함수 실행됨
            // DB에 있는 id와 pwd가 일치


//            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // authentication 객체가 session영역에 저장됨-> 로그인됨
            OAuth2UserImpl oAuth2User = (OAuth2UserImpl) authentication.getPrincipal();
            // authentication 객체가 session영역에 저장해햐 하고 그 방법으로 return 하면됨
            // 리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하려고함
            // jwt토큰을 사용하면 세션을 만들 이유가 없으나 권한 처리때문에 session에 넣어줌
        }catch (BadCredentialsException e){
            loginFailure(request,response);

        }
        catch (Exception e) {

            e.printStackTrace();
        }

        filterChain.doFilter(request,response);
    }


    protected void loginFailure(HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, String> result = new HashMap<>();
            result.put("\"msg\"", "\"id or password missmatch\"");
            response.getWriter().print(result);
            response.setStatus(401);
            response.addHeader("msg", "id or password missmatch");
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}

