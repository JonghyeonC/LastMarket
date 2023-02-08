package edu.ssafy.lastmarket.jwt;

import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.Location;
import edu.ssafy.lastmarket.domain.entity.Member;
import edu.ssafy.lastmarket.domain.entity.Role;
import io.jsonwebtoken.*;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtManager {

    @Value("${JWT.SECRET}")
    private String securityKey; // TODO 민감정보는 따로 분리하는 것이 좋다
    private final Long shortTokeneExpiredTime = 1000 * 60 * 30L;
    private final Long longTokenExpiredTime = 1000 * 60 * 60 * 24 * 7L;

    public String generateJwtToken(Member member, Location location, Image image) {
        Date now = new Date();
        log.info("[generate token]time={}", LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        return Jwts.builder()
                .setSubject(member.getUsername()) // 보통 username
                .setHeader(createHeader())
                .setClaims(createClaims(member, location, image)) // 클레임, 토큰에 포함될 정보
                .setExpiration(new Date(now.getTime() + shortTokeneExpiredTime)) // 만료일
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();
    }

    public String generateJwtFromToken(String token){
        Date now = new Date();
        return Jwts.builder()
                .setSubject(getUsername(token)) // 보통 username
                .setHeader(createHeader())
                .setClaims(createClaims(token)) // 클레임, 토큰에 포함될 정보
                .setExpiration(new Date(now.getTime() + shortTokeneExpiredTime)) // 만료일
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();

    }

    public String generateRefreshJwtToken(Member member) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(member.getUsername()) // 보통 username
                .setHeader(createHeader())
                .setClaims(createLongClaims()) // 클레임, 토큰에 포함될 정보
                .setExpiration(new Date(now.getTime() + longTokenExpiredTime)) // 만료일
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256"); // 해시 256 사용하여 암호화
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private Map<String, Object> createClaims(Member member, Location location, Image image) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getId());
        claims.put("nickname", member.getNickname());
        claims.put("username", member.getUsername());
        claims.put("profile", (image == null) ? "" : image.getImageURL());
        claims.put("localtion", (location == null) ? "" : location.toString());
        claims.put("role", member.getRole());

        return claims;
    }

    private Map<String, Object> createClaims(String token){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", getId(token));
        claims.put("nickname", getNickname(token));
        claims.put("username", getUsername(token));
        claims.put("profile", (getProfile(token) == null) ? "" : getProfile(token));
        claims.put("localtion", (getLocaltion(token)== null) ? "" : getLocaltion(token));
        claims.put("role", getRole(token));
        return claims;

    }

    private Map<String, Object> createLongClaims() {
        Map<String, Object> claims = new HashMap<>();
        return claims;
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token).getBody();
    }
    public Object getId(String token){
        return getClaims(token).get("Id");
    }
    public Object getNickname(String token) {
        return getClaims(token).get("nickname");
    }

    public String getUsername(String token){
        return (String) getClaims(token).get("username");
    }
    public Object getProfile(String token){
        return getClaims(token).get("profile");
    }

    public Object getLocaltion(String token){
        return getClaims(token).get("localtion");
    }

    public String getRole(String token){
        return (String)getClaims(token).get("role");

    }

    public Boolean isValidate(String token) {
        try {
            Jwts.parser().setSigningKey(securityKey).parseClaimsJws(token).getBody();
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.");
        }
        return false;
    }

    public String parseJwt(HttpServletRequest request){
        String headerAuth=null;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(int i=0;i<cookies.length;i++){
                if(cookies[i].getName().equals("Authentication")){
                    headerAuth = cookies[i].getValue();
                }
            }
            return headerAuth;
        }
        if(StringUtil.isNullOrEmpty(headerAuth)){
            headerAuth= request.getHeader("Authentication");
            return headerAuth;
        }
        return null;
    }
}
