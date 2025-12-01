package com.swe2.apigateway.filter;

import com.swe2.apigateway.model.TokenValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class RoleAuthorizationFilter extends AbstractGatewayFilterFactory<RoleAuthorizationFilter.Config> {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public RoleAuthorizationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Extract Authorization header
            if (!request.getHeaders().containsKey("Authorization")) {
                log.warn("Missing Authorization header for path: {}", request.getPath());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authHeader = request.getHeaders().getFirst("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Invalid Authorization header format for path: {}", request.getPath());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Call authentication service to validate token
            return validateToken(authHeader)
                    .flatMap(validationResponse -> {
                        if (!validationResponse.isValid()) {
                            log.warn("Invalid token for path: {}", request.getPath());
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }

                        // Check if role is required and validate
                        if (config.getRequiredRoles() != null && !config.getRequiredRoles().isEmpty()) {
                            String userRole = validationResponse.getRole();
                            if (userRole == null || !config.getRequiredRoles().contains(userRole.toLowerCase())) {
                                log.warn("User with role '{}' attempted to access path '{}' requiring roles: {}",
                                        userRole, request.getPath(), config.getRequiredRoles());
                                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                                return exchange.getResponse().setComplete();
                            }
                        }

                        // Add user information to headers for downstream services
                        ServerHttpRequest modifiedRequest = request.mutate()
                                .header("X-User-Id", String.valueOf(validationResponse.getUserId()))
                                .header("X-User-Email", validationResponse.getEmail())
                                .header("X-User-Role", validationResponse.getRole())
                                .build();

                        ServerWebExchange modifiedExchange = exchange.mutate()
                                .request(modifiedRequest)
                                .build();

                        log.info("Authorized user {} with role '{}' for path: {}",
                                validationResponse.getEmail(), validationResponse.getRole(), request.getPath());

                        return chain.filter(modifiedExchange);
                    })
                    .onErrorResume(error -> {
                        log.error("Error validating token: {}", error.getMessage());
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        };
    }

    private Mono<TokenValidationResponse> validateToken(String authHeader) {
        return webClientBuilder.build()
                .get()
                .uri("lb://AUTHENTICATION/api/auth/validate")
                .header("Authorization", authHeader)
                .retrieve()
                .bodyToMono(TokenValidationResponse.class)
                .doOnError(error -> log.error("Failed to call validation endpoint: {}", error.getMessage()));
    }

    public static class Config {
        private List<String> requiredRoles;

        public Config() {
        }

        public List<String> getRequiredRoles() {
            return requiredRoles;
        }

        public void setRequiredRoles(String roles) {
            if (roles != null && !roles.trim().isEmpty()) {
                this.requiredRoles = Arrays.stream(roles.split(","))
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .toList();
            }
        }
    }
}
