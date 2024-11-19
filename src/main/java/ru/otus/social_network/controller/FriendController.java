package ru.otus.social_network.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.api.v1.model.LoginPost500Response;
import ru.otus.social_network.service.FriendService;
import ru.otus.social_network.util.UserInterceptor;

@AllArgsConstructor
@RestController
@RequestMapping("/friend")
public class FriendController {

    private final UserInterceptor userInterceptor;
    private final FriendService friendService;

    @PutMapping("/set/{id}")
    public ResponseEntity<?> setFriendById(@PathVariable("id") String friendId, @NonNull HttpServletRequest request) {
        try {
            var userId = userInterceptor.getUserIdFromToken(request);
            var response = friendService.setFriendById(userId, friendId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginPost500Response()
                    .message("User not found")
                    .code(400)
                    .requestId("no-request-id"));
        }
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteFriendById(@PathVariable("id") String friendId, @NonNull HttpServletRequest request) {
        try {
            var userId = userInterceptor.getUserIdFromToken(request);
            var response = friendService.deleteFriendById(userId, friendId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginPost500Response()
                    .message("User not found")
                    .code(400)
                    .requestId("no-request-id"));
        }
    }
}
