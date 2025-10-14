package com.example.loginproject.domain.role;

import com.example.loginproject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {


    List<Role> findByUser(User user);

    void deleteByUser(User user);
}
