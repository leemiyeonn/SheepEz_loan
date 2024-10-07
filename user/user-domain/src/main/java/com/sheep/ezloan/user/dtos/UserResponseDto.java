package com.sheep.ezloan.user.dtos;

import com.sheep.ezloan.user.enums.RoleType;

public record UserResponseDto(Long userId, String username, String password, String email, RoleType role) {
}
