package com.example.loginproject.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 로그인 페이지 접근 제어
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 현재 사용자의 로그인 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isLoggedIn = authentication != null &&
                authentication.isAuthenticated() && // 인증된 사용자
                !authentication.getPrincipal().equals("anonymousUser");


        if (isLoggedIn) {
            // /boards로 리다이렉트
            response.sendRedirect("/boards");
            return false;
        }

        // 로그인 안 되어 있으면
        return true;
    }
}