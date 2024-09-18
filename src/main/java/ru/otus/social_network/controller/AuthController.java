package ru.otus.social_network.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.api.v1.exception.InvalidDataException;
import ru.otus.api.v1.model.*;
import ru.otus.social_network.service.AuthService;
import ru.otus.social_network.service.JwtService;
import ru.otus.social_network.service.UserService;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping(value = "/user/register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterPostRequest request) {
        try {
            var newUserId = userService.registerUser(request);
            var response = new UserRegisterPost200Response();
            response.setUserId(String.valueOf(newUserId));
            return ResponseEntity.ok(response);
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginPost500Response()
                    .message("Incorrect data")
                    .code(400)
                    .requestId("no-request-id"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginPost500Response()
                    .message("Internal Server Error")
                    .code(500)
                    .requestId("no-request-id"));
        }
    }

    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody LoginPostRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getId(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginPost500Response()
                    .message("Incorrect id or password")
                    .code(400)
                    .requestId("no-request-id"));
        }
        try {
            var user = authService.findAuthById(request.getId());
            var response = new LoginPost200Response();
            response.setToken(jwtService.generateToken(user));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginPost500Response()
                    .message("Internal Server Error")
                    .code(500)
                    .requestId("no-request-id"));
        }
    }
}
