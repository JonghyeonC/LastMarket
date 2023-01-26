package edu.ssafy.lastmarket.security;

import edu.ssafy.lastmarket.jwt.JwtAuthenticationFilter;
import edu.ssafy.lastmarket.jwt.JwtManager;
import edu.ssafy.lastmarket.security.user.PrincipalOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final PrincipalOAuth2UserService principalOauth2UserService;
    private final JwtManager jwtManager;



    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter=  new JwtAuthenticationFilter(principalOauth2UserService, jwtManager);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
        //임시로 처리
        http.authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/us  er").authenticated()
                .anyRequest().permitAll();
        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                })
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
                .and().
                successHandler(oAuth2AuthenticationSuccessHandler)
        ;
        return http.build();
    }
}