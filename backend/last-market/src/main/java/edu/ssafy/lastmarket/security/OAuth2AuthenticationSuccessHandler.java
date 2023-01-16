package edu.ssafy.lastmarket.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

//        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
//        User user = userServicLogic.findByMemUserId(principalDetails.getUser().getMemUserId());

        int attendance_point = 100;

//      System.out.println(user);
//      System.out.println(user.getMem_lastlogin_datetime());
//		System.out.println(check);


//		response.setStatus(307);

        response.setStatus(200);
//        RequestDispatcher rd = request.getRequestDispatcher("/oauth/redirect");
//        rd.forward(request, response);


    }

    private void writeTokenResponse(HttpServletResponse response, String token) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Authorization", token);

    }
}
