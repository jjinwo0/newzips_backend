package com.ssafy.happyhouse.global.interceptor;

import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.global.token.TokenManager;
import com.ssafy.happyhouse.global.token.TokenType;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS"))
            return true;

        String authorization = request.getHeader("Authorization");

        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();

        if (!TokenType.isAccessToken(tokenType))
            throw new RuntimeException("Not Match Token Type"); //TODO : Excpetion 수정 필요

        return true;
    }
}
