package ru.otus.social_network.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.api.v1.model.DialogMessage;
import ru.otus.api.v1.model.DialogUserIdSendPostRequest;
import ru.otus.api.v1.model.LoginPost500Response;
import ru.otus.social_network.service.DialogService;
import ru.otus.social_network.util.UserInterceptor;

@AllArgsConstructor
@RequestMapping("/dialog")
@RestController
public class DialogController {
    private final UserInterceptor userInterceptor;

    private final DialogService dialogService;

    @PostMapping("/{userId}/send")
    public ResponseEntity<?> sendMessage(@RequestBody DialogUserIdSendPostRequest message,
                                         @PathVariable("userId") String toUserId,
                                         @NonNull HttpServletRequest request) {
        // TODO добавить валидацию
        try {
            // Получаем информацию о текущем пользователе
            var existId = userInterceptor.getUserIdFromToken(request);
            var sendMessage = new DialogMessage();
            sendMessage.setFrom(existId);
            sendMessage.setTo(toUserId);
            sendMessage.setText(message.getText());
            var response = dialogService.sendMessage(sendMessage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginPost500Response()
                    .message("Internal Server Error")
                    .code(500)
                    .requestId("no-request-id"));
        }
    }

    @GetMapping("/{userId}/list")
    public ResponseEntity<?> getMessages(@PathVariable("userId") String fromUserId,
                                         @NonNull HttpServletRequest request) {
        // TODO добавить валидацию
        try {
            // Получаем информацию о текущем пользователе
            var existUserId = userInterceptor.getUserIdFromToken(request);
            var usersIds = new DialogMessage();
            usersIds.setFrom(existUserId);
            usersIds.setTo(fromUserId);
            var response = dialogService.getMessages(usersIds);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginPost500Response()
                    .message("Internal Server Error")
                    .code(500)
                    .requestId("no-request-id"));
        }
    }
}
