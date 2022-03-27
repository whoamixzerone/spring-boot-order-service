package com.zerone.springbootorderservice.interceptor;

import com.zerone.springbootorderservice.util.JwtTokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public TokenInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = jwtTokenProvider.getHeaderToken(request);
        System.out.println("access_token: "+accessToken);

        if(accessToken != null && jwtTokenProvider.isVerifyToken(accessToken)) {
            return true;
        }

        response.setStatus(401);
        response.setHeader("access_token", accessToken);
        response.setHeader("message", "Unauthorized");
        return false;
    }
}
