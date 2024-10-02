package com.sheep.ezloan.gateway.filter;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(0)
public class JwtAuthenticationFilter implements GlobalFilter {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public JwtAuthenticationFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // /auth 경로는 필터를 적용하지 않음
        if (path.startsWith("/api/v1/auth")) {
            return chain.filter(exchange);
        }

        // JWT 토큰 추출
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return handleUnauthorized(exchange);
        }

        String token = authHeader.substring(7); // "Bearer " 제거

        // Auth 서버에서 토큰 검증 요청
        return validateTokenWithAuthServer(token, exchange, chain);
    }

    private Mono<Void> validateTokenWithAuthServer(String token, ServerWebExchange exchange, GatewayFilterChain chain) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/api/v1/auth/validate-token")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .flatMap(responseBody -> handleSuccess(exchange, responseBody, chain))
                .onErrorResume(e -> handleUnauthorized(exchange));
    }

    private Mono<Void> handleSuccess(ServerWebExchange exchange, Map<String, Object> responseBody,
                                     GatewayFilterChain chain) {
        // 헤더에 유저 정보 추가
        addHeadersToRequest(exchange, responseBody);

        // 체인 필터 처리
        return chain.filter(exchange);
    }

    private void addHeadersToRequest(ServerWebExchange exchange, Map<String, Object> claims) {
        exchange.getRequest()
                .mutate()
                .header("X-User-Id", claims.get("sub").toString())
                .header("X-Role", claims.get("role").toString());
    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
