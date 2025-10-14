package com.example.loginproject.auth.service;

import com.example.loginproject.auth.dto.SignupRequest;
import com.example.loginproject.domain.role.Role;
import com.example.loginproject.domain.role.RoleName;
import com.example.loginproject.domain.role.RoleRepository;
import com.example.loginproject.domain.user.User;
import com.example.loginproject.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest request) {
        if (userRepository.existsByUserId(request.getUserId())){
            throw new IllegalArgumentException("이미 존재하는 아이디입니다");
        }

        User user = User.builder()
                .userId(request.getUserId())
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .userName(request.getUserName())
                .build();

        User savedUser = userRepository.save(user);

        createDefaultUser(savedUser);

    }

    private void createDefaultUser(User user) {
        Role role = Role.builder()
                .user(user)
                .roleName(RoleName.ROLE_USER)
                .build();
        roleRepository.save(role);
    }
}
