package com.example.loginproject.admin.dto;

import com.example.loginproject.domain.role.Role;
import com.example.loginproject.domain.role.RoleName;
import com.example.loginproject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자 권한 정보 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleResponse {


    private Long userNo;
    private String userId;
    private String userName;
    private List<RoleName> roles;
    private LocalDateTime createdDate;

    public static UserRoleResponse from(User user, List<Role> roles) {
        List<RoleName> roleNames = roles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        return UserRoleResponse.builder()
                .userNo(user.getUserNo())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .roles(roleNames)
                .createdDate(user.getCreatedDate())
                .build();
    }
}