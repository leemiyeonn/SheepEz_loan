package com.sheep.ezloan.auth.jwt;

import com.sheep.ezloan.user.enums.RoleType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}")
    private String secretKey;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private Key key;

    // secretKey 값을 Key 객체로 변환하는 작업을 초기화
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT 토큰 생성 메서드
    public String createToken(Long userId, String username, RoleType role) {
        Date date = new Date();

        // 토큰 만료시간 60분
        long TOKEN_TIME = 60 * 60 * 1000;

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("username", username)
                .claim("role", role)
                .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm) // Key 객체를 사용
                .compact();
    }

    // JWT 토큰에서 Claims를 추출하는 메서드
    public Claims extractClaims(String token) {
        try {
            // "Bearer " 부분을 제거하고 순수 토큰을 추출
            if (token.startsWith(BEARER_PREFIX)) {
                token = token.substring(BEARER_PREFIX.length());
            }

            // JWT 파싱 및 검증
            return Jwts.parserBuilder()
                    .setSigningKey(key) // Deprecated된 setSigningKey(String) 대신 key 객체 사용
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e) {
            throw new RuntimeException("JWT 검증 실패", e);
        }
    }

    public Map<String, Object> validateAndExtractClaims(String token) {
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        Claims claims = extractClaims(jwtToken);

        Map<String, Object> response = new HashMap<>();
        response.put("username", claims.get("username"));
        response.put("role", claims.get("role"));
        response.put("exp", claims.getExpiration());
        response.put("iat", claims.getIssuedAt());

        return response;
    }
}
