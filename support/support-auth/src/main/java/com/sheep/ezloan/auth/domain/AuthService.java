package com.sheep.ezloan.auth.domain;

import com.sheep.ezloan.auth.dtos.UserLoginRequest;
import com.sheep.ezloan.auth.dtos.UserSignupRequest;
import com.sheep.ezloan.user.dtos.UserRequestDto;
import com.sheep.ezloan.user.dtos.UserResponseDto;
import com.sheep.ezloan.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public String signup(UserSignupRequest request) {
        validateUsername(request.getUsername());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserRequestDto userRequestDto = new UserRequestDto(request.getUsername(), encodedPassword, request.getEmail());
        userService.createUser(userRequestDto);
        return request.getUsername();
    }

    public UserResponseDto login(UserLoginRequest request) throws IllegalAccessException {
        UserResponseDto userResponseDto = userService.findUserByUsername(request.getUsername());
        verifyPassword(request.getPassword(), userResponseDto.password());
        return userResponseDto;
    }

    private void validateUsername(String username) {
        if (userService.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists.");
        }
    }

    private void verifyPassword(String rawPassword, String encodedPassword) throws IllegalAccessException {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new IllegalAccessException("비밀번호가 일치하지 않습니다.");
        }
    }
}
