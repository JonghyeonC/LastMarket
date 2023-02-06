package edu.ssafy.lastmarket.jwt;

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


@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final PrincipalOAuth2UserService principalOAuth2UserService;

    private final JwtManager jwtManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String temp =parseJwt(request);

            if(temp!=null&& jwtManager.isVidate(temp)){
                String username = jwtManager.getUsername(temp);

                UserDetails userDetails = principalOAuth2UserService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            }
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
            e1.printStackTrace();
        }
    }

    private String parseJwt(HttpServletRequest request){
       String headerAuth=null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(int i=0;i<cookies.length;i++){
                if(cookies[i].getName().equals("Authentication")){
                    headerAuth = cookies[i].getValue();
                }
            }
            return headerAuth;
        }
        if(StringUtil.isNullOrEmpty(headerAuth)){
            headerAuth= request.getHeader("Authentication");
            return headerAuth;
        }
        return null;
    }
}

