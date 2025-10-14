package com.example.loginproject.admin.service;

import com.example.loginproject.admin.dto.UpdateRoleRequest;
import com.example.loginproject.admin.dto.UserRoleResponse;
import com.example.loginproject.domain.role.Role;
import com.example.loginproject.domain.role.RoleRepository;
import com.example.loginproject.domain.role.RoleName;
import com.example.loginproject.domain.user.User;
import com.example.loginproject.domain.user.UserRepository;
import com.example.loginproject.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자 기능 Service
 * - 사용자 조회/권한 관리/탈퇴 처리
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * 전체 사용자 목록 조회
     */
    @Transactional(readOnly = true)
    public List<UserRoleResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    List<Role> roles = roleRepository.findByUser(user);
                    return UserRoleResponse.from(user, roles);
                })
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자의 권한 조회
     */
    @Transactional(readOnly = true)
    public UserRoleResponse getUserRoles(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(UserException.UserNotFoundException::new);

        List<Role> roles = roleRepository.findByUser(user);

        return UserRoleResponse.from(user, roles);
    }

    /**
     * 사용자 권한 수정
     * - 기존 권한을 모두 삭제하고 새로운 권한으로 교체
     */
    @Transactional
    public UserRoleResponse updateUserRoles(Long userNo, UpdateRoleRequest request) {
        User user = userRepository.findById(userNo)
                .orElseThrow(UserException.UserNotFoundException::new);

        // 기존 권한 모두 삭제
        roleRepository.deleteByUser(user);

        // 새로운 권한 추가
        List<Role> newRoles = request.getRoleNames().stream()
                .map(roleName -> Role.builder()
                        .user(user)
                        .roleName(roleName)
                        .build())
                .collect(Collectors.toList());

        roleRepository.saveAll(newRoles);

        return UserRoleResponse.from(user, newRoles);
    }

    /**
     * 사용자 삭제 (탈퇴 처리)
     */
    @Transactional
    public void deleteUser(Long userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(UserException.UserNotFoundException::new);

        // 권한 먼저 삭제
        roleRepository.deleteByUser(user);

        // 사용자 삭제
        userRepository.delete(user);
    }
}