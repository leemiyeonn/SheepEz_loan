package com.sheep.ezloan.auth.controller;

import com.sheep.ezloan.auth.domain.AuthService;
import com.sheep.ezloan.auth.dtos.UserLoginRequest;
import com.sheep.ezloan.auth.dtos.UserSignupRequest;
import com.sheep.ezloan.auth.jwt.JwtUtil;
import com.sheep.ezloan.user.dtos.UserResponseDto;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody @Valid UserSignupRequest request) {
        String username = authService.signup(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", username + " registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid UserLoginRequest request)
            throws IllegalAccessException {
        UserResponseDto loginedUser = authService.login(request);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("role", loginedUser.role().toString());

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String token) {
        try {
            Map<String, Object> claims = jwtUtil.validateAndExtractClaims(token);
            return ResponseEntity.ok(claims);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Unauthorized")); // HTTP 401 Unauthorized
        }
    }
}
