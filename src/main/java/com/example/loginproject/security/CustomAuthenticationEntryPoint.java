package com.example.loginproject.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 인증되지 않은 사용자가 보호된 페이지 접근 시 처리
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String requestUri = request.getRequestURI();

        if (requestUri.startsWith("/api/")) {
            sendJsonErrorResponse(response);
        } else {
            response.sendRedirect("/login");
        }
    }

    /**
     * JSON 에러 응답 전송
     */
    private void sendJsonErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", "인증이 필요합니다");
        errorResponse.put("data", null);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}