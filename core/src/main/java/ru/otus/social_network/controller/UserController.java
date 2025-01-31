package ru.otus.social_network.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.social_network.model.LoginPost500Response;
import ru.otus.social_network.service.UserService;

@AllArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") String id) {
        try {
            var response = userService.findUserById(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginPost500Response()
                    .message("User not found")
                    .code(400)
                    .requestId("no-request-id"));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByFirstAndLastName(@RequestParam String firstName, @RequestParam String secondName) {
        try {
            var response = userService.findUserByFirstAndLastName(firstName, secondName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginPost500Response()
                    .message("User not found")
                    .code(400)
                    .requestId("no-request-id"));
        }
    }
}
