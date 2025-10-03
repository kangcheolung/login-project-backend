package com.example.loginproject.domain.role;

import com.example.loginproject.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, length = 20)
    private RoleName roleName;

    @Builder
    public Role(User user, RoleName roleName) {
        this.user = user;
        this.roleName = roleName;
    }

    public void updateRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}