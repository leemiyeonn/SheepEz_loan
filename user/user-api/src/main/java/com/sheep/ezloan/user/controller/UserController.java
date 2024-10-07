package com.sheep.ezloan.user.controller;

import com.sheep.ezloan.user.utils.SecurityUtil;
import com.sheep.ezloan.user.dtos.UserRequestDto;
import com.sheep.ezloan.user.dtos.UserResponseDto;
import com.sheep.ezloan.user.enums.RoleType;
import com.sheep.ezloan.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto request) {
        UserResponseDto userResponse = userService.createUser(request);
        return ResponseEntity.ok(userResponse);
    }

    // 사용자명으로 사용자 조회
    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> findUserByUsername(@PathVariable(name = "username") String username) {
        UserResponseDto userResponse = userService.findUserByUsername(username);
        return ResponseEntity.ok(userResponse);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public UserResponseDto getUserInfo() {
        Long userId = SecurityUtil.getCurrentUserId();
        return userService.getUserInfo(userId);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping
    public String deleteUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        return userService.deleteUser(userId);
    }

    // 관리자 전용: 모든 사용자 조회
    @PreAuthorize("hasRole('MASTER')")
    @GetMapping("/all")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // 관리자 전용: 사용자 권한 변경
    @PreAuthorize("hasRole('MASTER')")
    @PatchMapping("/{userId}/role")
    public String changeUserRole(@PathVariable Long userId, @RequestBody RoleType newRole) {
        return userService.changeUserRole(userId, newRole);
    }

    // 관리자 전용: 사용자 검색
    @PreAuthorize("hasRole('MASTER')")
    @GetMapping("/search")
    public List<UserResponseDto> searchUsers(@RequestParam String username) {
        return userService.searchUsers(username);
    }


}
