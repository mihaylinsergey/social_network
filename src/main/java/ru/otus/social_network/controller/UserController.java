package ru.otus.social_network.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.api.v1.model.User;
import ru.otus.social_network.service.UserService;

@AllArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userService.findUserById(id).orElseThrow();
    }
}
