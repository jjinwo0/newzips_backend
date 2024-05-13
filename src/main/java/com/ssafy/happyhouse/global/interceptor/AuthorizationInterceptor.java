package com.ssafy.happyhouse.global.interceptor;

import com.ssafy.happyhouse.entity.member.Member;
import com.ssafy.happyhouse.entity.member.constant.Role;
import com.ssafy.happyhouse.global.token.TokenManager;
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
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String role = (String) tokenClaims.get("role");

        if (!role.equals(Role.ADMIN.toString())) {
            throw new RuntimeException("Not Admin"); //TODO : Exception 수정 필요
        }

        return true;
    }
}
