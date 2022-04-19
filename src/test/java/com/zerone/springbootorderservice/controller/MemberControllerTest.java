package com.zerone.springbootorderservice.controller;

import com.zerone.springbootorderservice.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Key;
import java.util.Date;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    @LocalServerPort
    private int port;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-valid-in-seconds}")
    private long tokenValidInMilliseconds;
    private Key key;
    private final long ACCESS_TOKEN_VALID_TIME = 1000 * 3L;

    private RestTemplate restTemplate = new RestTemplate();

    private URI uri(String path) throws URISyntaxException {
        return new URI(format("http://localhost:%d%s", port, path));
    }

    @BeforeEach
    public void setUp() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createTokenTest() throws InterruptedException {
        Claims claims = Jwts.claims();
        claims.put("email", "admin1@gmail.com");
        claims.put("token_type", "access_token");

        long now = (new Date()).getTime();
//        Date valid = new Date(now + this.tokenValidInMilliseconds);
        Date valid = new Date(now + this.ACCESS_TOKEN_VALID_TIME);

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setExpiration(valid)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        System.out.println(token);

        Thread.sleep(5000);

        assertThrows(ExpiredJwtException.class, () -> {
            Claims accessClaims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(accessClaims);
        });
    }

    @Test
    public void loginTest() throws URISyntaxException {
        Member member = Member.builder()
                .email("admin1@gmail.com")
                .password("1234")
                .build();
        HttpEntity<Member> body = new HttpEntity<>(member);
        ResponseEntity<String> response = restTemplate.exchange(uri("/member/login"), HttpMethod.POST, body, String.class);
        System.out.println(response.getBody());

        assertEquals(200, response.getStatusCodeValue());
        assertThat(response.getBody(), containsString("access_token"));
    }

    @Test
    public void notMemberLoginTest() throws URISyntaxException {
        Member member = Member.builder()
                .email("admin99@gmail.com")
                .password("1234")
                .build();
        HttpEntity<Member> body = new HttpEntity<>(member);
        ResponseEntity<String> response = restTemplate.exchange(uri("/member/login"), HttpMethod.POST, body, String.class);
        System.out.println(response.getBody());

        assertEquals(200, response.getStatusCodeValue());
        assertThat(response.getBody(), containsString("access_token"));
    }
}