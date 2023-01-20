package edu.ssafy.lastmarket.security;

import edu.ssafy.lastmarket.service.PrincipalOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final PrincipalOAuth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        
        //임시로 처리
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user").authenticated()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        http.exceptionHandling()
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        });
        http.csrf().disable();
        http.headers()
                .frameOptions()
                .sameOrigin();
        http.oauth2Login()
//                .loginPage("/login")
//                .defaultSuccessUrl("/")
                .userInfoEndpoint() // 필수
                .userService(principalOauth2UserService)
                .and().successHandler(oAuth2AuthenticationSuccessHandler)
        ;
        return http.build();
    }
}
