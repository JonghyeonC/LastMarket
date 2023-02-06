package edu.ssafy.lastmarket.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = UUID.randomUUID().toString();
        request.setAttribute("uuid", uuid);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        log.info("[{}][{}|{}] connection ", uuid, method, uri);
        if (request.getCookies() == null) {
            return true;
        }
        Cookie authentication = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("Authentication"))
                .findFirst()
                .orElse(null);
        if (authentication != null) {
            log.info("[{}][{}|{}] cookie=EXIST", uuid, method, uri);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uuid = (String) request.getAttribute("uuid");
        log.info("[{}][{}|{}] end", uuid, request.getMethod(), request.getRequestURI());
    }
}
