package ru.otus.social_network.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.social_network.service.JwtService;

@Component
@AllArgsConstructor
public class UserInterceptor {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;

    public String getUserIdFromToken(HttpServletRequest request) {
        var authHeader = request.getHeader(HEADER_NAME);
        var jwt = authHeader.substring(BEARER_PREFIX.length());
        var userId = jwtService.extractUserId(jwt);
        return userId;
    }
}
