package com.example.loginproject.auth.controller;

import com.example.loginproject.auth.dto.SignupRequest;
import com.example.loginproject.auth.service.AuthService;
import com.example.loginproject.common.CommonResponse;
import com.example.loginproject.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public CommonResponse<Void> signup(@RequestBody SignupRequest request) {
        authService.signup(request);
        return CommonResponse.ok();
    }

    @GetMapping("/session")
    public CommonResponse<Map<String, Object>> getSession(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        if (userDetails == null) {
            return CommonResponse.ok(Map.of("isLoggedIn", false));
        }

        Map<String, Object> sessionInfo = new HashMap<>();
        sessionInfo.put("isLoggedIn", true);
        sessionInfo.put("userNo", userDetails.getUserNo());
        sessionInfo.put("userId", userDetails.getUsername());
        sessionInfo.put("userName", userDetails.getUserName());
        sessionInfo.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        return CommonResponse.ok(sessionInfo);
    }
}