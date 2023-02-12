package edu.ssafy.lastmarket.interceptor;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

//TODO : 로그 메세지에 대해서 고민해보기 + POST의 Body를 logging을 해야될까?
@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = UUID.randomUUID().toString();
        request.setAttribute("uuid", uuid);
        String method = request.getMethod();
        String uri = request.getRequestURI();

        String queryString = request.getQueryString();
        if (StringUtil.isNullOrEmpty(queryString)) {
            log.info("[{}][{}][{}] connection ", method, uri, uuid);
        } else {
            log.info("[{}][{}][{}][{}] connection ", method, uri, queryString, uuid);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uuid = (String) request.getAttribute("uuid");
        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info("[{}][{}][{}] end", method, uri, uuid);
    }
}
