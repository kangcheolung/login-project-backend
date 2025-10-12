package com.example.loginproject.admin.dto;

import com.example.loginproject.domain.role.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 사용자 권한 수정 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequest {

    private List<RoleName> roleNames;
}