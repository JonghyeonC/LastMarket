package edu.ssafy.lastmarket.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtRefreshFilter extends OncePerRequestFilter {


    private final JwtManager jwtManager;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken =jwtManager.parseJwt(request);

        if(jwtToken!=null&& jwtManager.isValidate(jwtToken)){
            String newToken = jwtManager.generateJwtFromToken(jwtToken);
            Cookie cookie = new Cookie("Authentication", newToken);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            response.setHeader("Authentication", newToken);
        }

        filterChain.doFilter(request,response);
    }
}
