package com.zerone.springbootorderservice.controller;

import com.zerone.springbootorderservice.entity.Member;
import com.zerone.springbootorderservice.repository.MemberRepository;
import com.zerone.springbootorderservice.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${secret.access}")
    private String SECRET_KEY;

    private final long ACCESS_TOKEN_VALID_TIME = 1000 * 5L;

    @BeforeEach
    public void setUp() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());

        memberRepository.save(Member.builder()
                .id("admin1")
                .password("1234")
                .name("관리자1")
                .build()
        );
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createTokenTest() throws InterruptedException {
        Claims claims = Jwts.claims();
        claims.put("userId", "admin1");
        Date now = new Date();

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        System.out.println(token);

        Thread.sleep(5000);

        assertThrows(ExpiredJwtException.class, () -> {
            Claims accessClaims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(accessClaims);
        });
    }

    @Test
    public void loginTest() {
        String userId = "admin1";
        String pwd = "1234";

        /**
         * 1. 토큰을 생성
         * 2. given().willReturn({"accessToken": token})
         * 3.
         */

        /**
         * id,pwd가 일치하는 사용자를 조회,
         * JWT Token을 생성해서 전달
         * {'accessToken': token}
         */
    }
}