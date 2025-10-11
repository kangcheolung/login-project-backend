package com.example.loginproject.security;

import com.example.loginproject.domain.role.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;  // Spring Security User

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails extends User {

    private final Long userNo;
    private final String userName;

    public CustomUserDetails(
            String userId,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            Long userNo,
            String userName
    ) {
        super(userId, password, authorities);
        this.userNo = userNo;
        this.userName = userName;
    }

    public static CustomUserDetails from(
            com.example.loginproject.domain.user.User user,  // 도메인 User
            Collection<Role> roles
    ) {
        Collection<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getUserId(),
                user.getUserPassword(),
                authorities,
                user.getUserNo(),
                user.getUserName()
        );
    }
}