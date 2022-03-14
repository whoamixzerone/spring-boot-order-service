package com.zerone.springbootorderservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${secret.access}")
    private String SECRET_KEY;

    // 토큰 유효기간 1일
    private final long ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 24L;

    @PostConstruct
    protected void init() {
        this.SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    public String createAccessToken(String userId) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)  // 정보 저장
                .setIssuedAt(now)   // 발행 시간 정보
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Request Header에서 Bearer 제외한 token 값을 가져온다.
    public String getHeaderToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("authentication").split("Bearer ")[1];

            return token;
        } catch (IndexOutOfBoundsException exception) {
            throw new IndexOutOfBoundsException();
        }
    }

    public Claims getClaimsToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isVerifyToken(String token) {
        try {
            Claims claims = getClaimsToken(token);

            return true;
        } catch (ExpiredJwtException exception) {
            return false;
        }
    }
}
