package com.sheep.ezloan.user.model;

import com.sheep.ezloan.user.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class User {

    private Long userId;

    private String username;

    private String password;

    private String email;

    private RoleType role;

    private Boolean isPublic = true;

    private Boolean isDelete = false;

    public User changeRole(RoleType newRole) {
        return new User(
                this.userId,
                this.username,
                this.password,
                this.email,
                newRole,
                this.isPublic,
                this.isDelete
        );
    }

    public User softDelete() {
        return new User(
                this.userId,
                this.username,
                this.password,
                this.email,
                this.role,
                this.isPublic,
                true
        );
    }
}
