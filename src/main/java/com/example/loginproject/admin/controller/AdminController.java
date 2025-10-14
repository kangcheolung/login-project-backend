package com.example.loginproject.admin.controller;

import com.example.loginproject.admin.dto.UpdateRoleRequest;
import com.example.loginproject.admin.dto.UserRoleResponse;
import com.example.loginproject.admin.service.AdminService;
import com.example.loginproject.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 전체 사용자 목록 조회
     * GET /api/admin/users
     */
    @GetMapping("/users")
    public CommonResponse<List<UserRoleResponse>> getAllUsers() {
        List<UserRoleResponse> users = adminService.getAllUsers();
        return CommonResponse.ok(users);
    }

    /**
     * 특정 사용자의 권한 조회
     * GET /api/admin/users/{userNo}/roles
     */
    @GetMapping("/users/{userNo}/roles")
    public CommonResponse<UserRoleResponse> getUserRoles(
            @PathVariable Long userNo
    ) {
        UserRoleResponse userRoleResponse = adminService.getUserRoles(userNo);
        return CommonResponse.ok(userRoleResponse);
    }

    /**
     * 사용자 권한 수정
     * PUT /api/admin/users/{userNo}/roles
     */
    @PutMapping("/users/{userNo}/roles")
    public CommonResponse<UserRoleResponse> updateUserRoles(
            @PathVariable Long userNo,
            @RequestBody UpdateRoleRequest request
    ) {
        UserRoleResponse userRoleResponse = adminService.updateUserRoles(userNo, request);
        return CommonResponse.ok(userRoleResponse);
    }

    /**
     * 사용자 삭제 (탈퇴 처리)
     */
    @DeleteMapping("/users/{userNo}")
    public CommonResponse<Void> deleteUser(
            @PathVariable Long userNo
    ) {
        adminService.deleteUser(userNo);
        return CommonResponse.ok(null);
    }
}