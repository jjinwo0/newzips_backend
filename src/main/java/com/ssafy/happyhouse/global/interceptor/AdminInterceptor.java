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
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        Member loginMember = (Member) session.getAttribute("userInfo");

        if (!loginMember.getRole().getRoleType().equals("ADMIN")){
            log.info("Login Member is not ADMIN...");

            response.sendRedirect("/");

            return false;
        }

        return true;
    }
}
