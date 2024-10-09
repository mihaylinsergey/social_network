package ru.otus.social_network.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.api.v1.model.LoginPost500Response;
import ru.otus.api.v1.model.Post;
import ru.otus.api.v1.model.PostCreatePostRequest;
import ru.otus.api.v1.model.UserRegisterPost200Response;
import ru.otus.social_network.exception.InvalidDataException;
import ru.otus.social_network.service.JwtService;
import ru.otus.social_network.service.PostService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String HEADER_NAME = "Authorization";
    private final JwtService jwtService;
    private final PostService postService;

    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPost(@RequestBody PostCreatePostRequest post, @NonNull HttpServletRequest request) {
        try {
            // Получаем информацию о текущем пользователе
            var authHeader = request.getHeader(HEADER_NAME);
            var jwt = authHeader.substring(BEARER_PREFIX.length());
            var userId = jwtService.extractUserId(jwt);
            Post newPost = new Post();
            newPost.setText(post.getText());
            newPost.setAuthorUserId(userId);
            var newPostId = postService.createPost(newPost);
            var response = new UserRegisterPost200Response();
            response.setUserId(String.valueOf(newPostId));
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
}
