package com.zerone.springbootorderservice.interceptor;

import com.zerone.springbootorderservice.util.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private JwtTokenProvider jwtTokenProvider;

    public TokenInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getHeaderToken(request);
        String requestURI = request.getRequestURI();

        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            logger.info("JWT 토큰 인증 완료, uri: {}", requestURI);
            return true;
        }

        logger.info("유효한 JWT 토큰이 없습니다. uri: {}", requestURI);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    private String getHeaderToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
