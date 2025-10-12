package com.example.loginproject.security;

import com.example.loginproject.domain.role.Role;
import com.example.loginproject.domain.role.RoleRepository;
import com.example.loginproject.domain.user.User;
import com.example.loginproject.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUserId(username);
        List<Role> roles = findRolesByUser(user);
        validateUserHasRoles(roles);

        return CustomUserDetails.from(user, roles);
    }

    private User findUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다"));
    }

    private List<Role> findRolesByUser(User user) {
        return roleRepository.findByUser(user);
    }

    private void validateUserHasRoles(List<Role> roles) {
        if (roles.isEmpty()) {
            throw new UsernameNotFoundException("권한이 없는 사용자입니다");
        }
    }
}