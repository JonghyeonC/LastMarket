package edu.ssafy.lastmarket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class RedirectController {

    @GetMapping("/signup")
    public ResponseEntity<?> redirectSignup(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("Authentication");
        Cookie cookie = new Cookie("Authentication", token);
        cookie.setPath("/");
        cookie.setDomain("https://i8d206.p.ssafy.io");
        response.addHeader("Authentication",token);
        response.addCookie(cookie);
        response.addHeader("Location","https://i8d206.p.ssafy.op/signup");
        return new ResponseEntity<>(HttpStatus.TEMPORARY_REDIRECT);
    }


    @GetMapping("/index")
    public ResponseEntity<?> redirectIndex(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("Authentication");
        Cookie cookie = new Cookie("Authentication", token);
        cookie.setPath("/");
        cookie.setDomain("https://i8d206.p.ssafy.io");
        response.addHeader("Authentication",token);
        response.addCookie(cookie);
        response.addHeader("Location","https://i8d206.p.ssafy.op/");
        return new ResponseEntity<>(HttpStatus.TEMPORARY_REDIRECT);
    }
}
