package com.sheep.ezloan.user.entity;

import com.sheep.ezloan.support.entity.BaseEntity;
import com.sheep.ezloan.user.enums.RoleType;
import com.sheep.ezloan.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
@Table(name = "p_users")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", length = 10, nullable = false)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type", nullable = false)
    private RoleType role;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = true;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;

    public UserEntity(String username, String password, String email, RoleType roleType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = roleType;
    }

    public void softDelete() {
        this.isDelete = true;
    }

    public void setRole(RoleType newRole) {
        this.role = newRole;
    }

}
