package ru.otus.social_network.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.api.v1.model.*;
import ru.otus.social_network.exception.InvalidDataException;
import ru.otus.social_network.service.PostService;
import ru.otus.social_network.util.UserInterceptor;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {

    private final UserInterceptor userInterceptor;
    private final PostService postService;

    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPost(@RequestBody PostCreatePostRequest post, @NonNull HttpServletRequest request) {
        try {
            // Получаем информацию о текущем пользователе
            var userId = userInterceptor.getUserIdFromToken(request);
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

    @GetMapping("/feed")
    public ResponseEntity<?> getPostFeed(@NonNull HttpServletRequest request) {
        try {
            var userId = userInterceptor.getUserIdFromToken(request);
            var response = postService.getPostFeed(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginPost500Response()
                    .message("Internal Server Error")
                    .code(500)
                    .requestId("no-request-id"));
        }
    }

    @PostMapping(value = "/update", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePost(@RequestBody Post post, @NonNull HttpServletRequest request) {
        try {
            // Получаем информацию о текущем пользователе
            var userId = userInterceptor.getUserIdFromToken(request);
            post.setAuthorUserId(userId);
            var response = postService.updatePost(post);
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

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") String id, @NonNull HttpServletRequest request) {
        try {
            // Получаем информацию о текущем пользователе
            var userId = userInterceptor.getUserIdFromToken(request);
            Post post = new Post();
            post.setId(id);
            post.setAuthorUserId(userId);
            var response = postService.deletePost(post);
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

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<?> getPost(@PathVariable("id") String id, @NonNull HttpServletRequest request) {
        try {
            // Получаем информацию о текущем пользователе
            var userId = userInterceptor.getUserIdFromToken(request);
            Post post = new Post();
            post.setId(id);
            post.setAuthorUserId(userId);
            var response = postService.getPost(post);
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
