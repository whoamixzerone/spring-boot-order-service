package com.zerone.springbootorderservice.controller;

import com.zerone.springbootorderservice.entity.Member;
import com.zerone.springbootorderservice.service.MemberService;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    @LocalServerPort
    private int port;

//    @MockBean
//    private JwtTokenProvider jwtTokenProvider;

    @Value("${secret.access}")
    private String SECRET_KEY;

    private final long ACCESS_TOKEN_VALID_TIME = 1000 * 5L;

    private RestTemplate restTemplate = new RestTemplate();

    private URI uri(String path) throws URISyntaxException {
        return new URI(format("http://localhost:%d%s", port, path));
    }

    @BeforeEach
    public void setUp() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
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
    public void loginTest() throws URISyntaxException {
        Member member = Member.builder()
                .userId("admin1")
                .password("1234")
                .build();
        HttpEntity<Member> body = new HttpEntity<>(member);
        ResponseEntity<String> response = restTemplate.exchange(uri("/member/login"), HttpMethod.POST, body, String.class);

        assertEquals(200, response.getStatusCodeValue());

        System.out.println(response.getBody());
    }
}