package com.sheep.ezloan.user.service;

import com.sheep.ezloan.user.dtos.UserRequestDto;
import com.sheep.ezloan.user.dtos.UserResponseDto;
import com.sheep.ezloan.user.enums.RoleType;
import com.sheep.ezloan.user.model.User;
import com.sheep.ezloan.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto request) {
        User user = new User(
                null,
                request.username(),
                request.password(),
                request.email(),
                RoleType.USER,
                true,
                false
        );
        userRepository.save(user);

        // 응답 객체로 변환하여 반환
        return new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole()
        );
    }

    // 사용자 ID로 사용자 정보 조회
    public UserResponseDto getUserInfo(Long userId) {
        User user = getUserFromId(userId);
        return new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole()
        );
    }

    // 사용자명으로 사용자 조회
    public UserResponseDto findUserByUsername(String username) {
        User userEntity = userRepository.findByUsernameAndIsDeleteFalse(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 응답 객체로 변환하여 반환
        return new UserResponseDto(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getRole()
        );
    }

    // 사용자 삭제 (소프트 삭제)
    @Transactional
    public String deleteUser(Long userId) {
        User user = getUserFromId(userId);
        User updatedUser = user.softDelete();
        userRepository.save(updatedUser);
        return "User ID: " + updatedUser.getUserId() + " Soft Delete is complete";
    }

    // 관리자 전용: 모든 사용자 조회
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAllByIsDeleteFalse();
        return users.stream()
                .map(user -> new UserResponseDto(user.getUserId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
    }

    // 관리자 전용: 사용자 권한 변경
    @Transactional
    public String changeUserRole(Long userId, RoleType newRole) {
        User user = getUserFromId(userId);
        User updatedUser = user.changeRole(newRole);
        userRepository.save(updatedUser);
        return "User ID: " + updatedUser.getUserId() + " Role changed to " + newRole;
    }

    // 관리자 전용: 사용자 검색
    public List<UserResponseDto> searchUsers(String username) {
        List<User> users = userRepository.findByUsernameContainingAndIsDeleteFalse(username);
        return users.stream()
                .map(user -> new UserResponseDto(user.getUserId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());
    }

    private User getUserFromId(Long userId) {
        return userRepository.findByUserIdAndIsDeleteFalse(userId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found User or User is Deleted"));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
