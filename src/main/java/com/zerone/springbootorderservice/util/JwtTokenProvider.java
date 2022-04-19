package com.zerone.springbootorderservice.util;

import com.zerone.springbootorderservice.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final String secret;
    private final long tokenValidInMilliseconds;

    private Key key;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-valid-in-seconds}") long tokenValidInMilliseconds) {
        this.secret = secret;
        this.tokenValidInMilliseconds = tokenValidInMilliseconds * 1000;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Member member) {
        Claims claims = Jwts.claims();
        claims.put("email", member.getEmail());
        claims.put("token_type", "access_token");

        long now = (new Date()).getTime();
        Date valid = new Date(now + this.tokenValidInMilliseconds);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)  // 정보 저장
                .setExpiration(valid)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims getAuthentication(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getAuthentication(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
