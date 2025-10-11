package com.example.loginproject.security.handler;

import com.example.loginproject.common.CommonResponse;
import com.example.loginproject.security.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info("로그인 성공: userId={}, userName={}",
                userDetails.getUsername(),
                userDetails.getUserName());

        Map<String, Object> userInfo = createUserInfo(userDetails);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(
                objectMapper.writeValueAsString(CommonResponse.ok(userInfo))
        );
    }

    private Map<String, Object> createUserInfo(CustomUserDetails userDetails) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userNo", userDetails.getUserNo());
        userInfo.put("userId", userDetails.getUsername());
        userInfo.put("userName", userDetails.getUserName());
        userInfo.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        return userInfo;
    }
}