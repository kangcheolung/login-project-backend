package com.example.loginproject.config;

import com.example.loginproject.security.handler.UserAuthenticationFailureHandler;
import com.example.loginproject.security.handler.UserAuthenticationSuccessHandler;
import com.example.loginproject.security.handler.UserLogoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserAuthenticationSuccessHandler authenticationSuccessHandler;
    private final UserAuthenticationFailureHandler authenticationFailureHandler;
    private final UserLogoutHandler logoutHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // URL 별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // 로그인/로그아웃은 누구나 접근 가능
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")  // 관리자 API는 ADMIN 권한 필요
                        .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
                )

                // 폼 로그인 설정
                .formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login")  // 로그인 처리 URL
                        .usernameParameter("userId")  // JSON의 userId 필드명
                        .passwordParameter("password")  // JSON의 password 필드명
                        .successHandler(authenticationSuccessHandler)  // 로그인 성공 시 Handler
                        .failureHandler(authenticationFailureHandler)  // 로그인 실패 시 Handler
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")  // 로그아웃 URL
                        .logoutSuccessHandler(logoutHandler)  // 로그아웃 성공 시 Handler
                        .invalidateHttpSession(true)  // 세션 무효화
                        .deleteCookies("JSESSIONID")  // 세션 쿠키 삭제
                );
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:5173"
        ));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
