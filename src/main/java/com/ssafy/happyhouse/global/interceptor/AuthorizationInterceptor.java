package com.ssafy.happyhouse.global.interceptor;

import com.ssafy.happyhouse.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        Member loginMember = (Member) session.getAttribute("userInfo");

        if (loginMember == null){
            log.info("Login Member is not found...");

            response.sendRedirect("/?loginRequired=true");

            return false;
        }

        return true;
    }
}
