package com.spring.blog.config.jwt;

import com.spring.blog.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;


// application-jwt.properties -> application.properties -> JwtProperties -> TokenProvider

@Service
@RequiredArgsConstructor

public class TokenProvider {

    // getter를 이용해 properties 파일에 작성한 데이터 가져오기
    private final JwtProperties jwtProperties;


    // 토큰 발급
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // * 토큰 생성 로직
    private String makeToken(Date expiry,User user) {

        // 현재 시간으로 토큰 발급 날짜 저장할 준비
        Date now = new Date();

        // 헤더타입 : JWT , 내용 : application.properties 으로 토큰 생성 등등
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getLoginId())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // * 발급된 토큰 유효한지, 유효하지 않은지 검증
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
            new org.springframework.security.core.userdetails.User(claims.getSubject(),
                    "", authorities), token, authorities);
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    // 클레임 정보(실제 개인정보)는 외부에서 직접 호출 못하고, 오로지 토큰 제공 클래스 내부에서만 호출되도록 처리
    private Claims getClaims(String token) {

        // 비밀키 제공, 토큰 제공, 유저정보 리턴
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
