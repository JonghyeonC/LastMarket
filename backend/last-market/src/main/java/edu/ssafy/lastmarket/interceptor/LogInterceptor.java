package edu.ssafy.lastmarket.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[{}] connection ", request.getRequestURI());
        if (request.getCookies() == null) {
            return true;
        }
        Cookie authentication = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("Authentication"))
                .findFirst()
                .orElse(null);
        if (authentication != null) {
            log.info("[{}] cookie={}", request.getRequestURI(), authentication.getValue());
        }
        return true;
    }
}
