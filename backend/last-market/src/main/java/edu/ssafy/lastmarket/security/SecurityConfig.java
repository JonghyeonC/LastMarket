package edu.ssafy.lastmarket.security;

import edu.ssafy.lastmarket.jwt.JwtAuthenticationFilter;
import edu.ssafy.lastmarket.jwt.JwtManager;
import edu.ssafy.lastmarket.jwt.JwtRefreshFilter;
import edu.ssafy.lastmarket.security.user.PrincipalOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final PrincipalOAuth2UserService principalOauth2UserService;
    private final JwtManager jwtManager;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000","https://i8d206.p.ssafy.io"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedMethods(
                Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;

    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        //임시로 처리
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET)
                .permitAll()
                .anyRequest().authenticated();

        http.formLogin().disable();
        http.httpBasic().disable();
        http.csrf().disable();
        http.cors();
        http.headers()
                .frameOptions()
                .sameOrigin();
        http.oauth2Login()
                .userInfoEndpoint() // 필수
                .userService(principalOauth2UserService)
                .and().
                successHandler(oAuth2AuthenticationSuccessHandler);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(principalOauth2UserService, jwtManager);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

//        http.addFilterAfter(new JwtRefreshFilter(jwtManager),JwtAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                });

        return http.build();


    }
}
