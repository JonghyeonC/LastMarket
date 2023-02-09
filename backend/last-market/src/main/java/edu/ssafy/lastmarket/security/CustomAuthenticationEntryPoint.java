package edu.ssafy.lastmarket.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String exception = (String) request.getAttribute("exception");
        String token = (String) request.getAttribute("token");
        if(exception.equals("NeedNicknameAndLocation")){
            Cookie cookie = new Cookie("Authentication", token);
            cookie.setPath("/");
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            response.addHeader("Authentication", token);
            response.setStatus(302);
            response.setHeader("Location", "/signup");
            return;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
