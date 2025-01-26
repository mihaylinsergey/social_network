package ru.otus.social_network.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.api.v1.model.DialogMessage;
import ru.otus.api.v1.model.DialogUserIdSendPostRequest;
import ru.otus.api.v1.model.LoginPost500Response;
import ru.otus.social_network.service.DialogRedisService;
import ru.otus.social_network.service.DialogService;
import ru.otus.social_network.util.UserInterceptor;
import java.util.List;

@RequestMapping("/dialog")
@RestController
public class DialogController {
    private final UserInterceptor userInterceptor;

    private final DialogService dialogService;

    private final DialogRedisService dialogRedisService;

    @Value("${dialog.service.in.memory}")
    private boolean IN_MEMORY;

    private static final Logger logger = LoggerFactory.getLogger(DialogController.class);

    public DialogController(UserInterceptor userInterceptor, DialogService dialogService, DialogRedisService dialogRedisService) {
        this.userInterceptor = userInterceptor;
        this.dialogService = dialogService;
        this.dialogRedisService = dialogRedisService;
    }

    @PostMapping("/{userId}/send")
    public ResponseEntity<?> sendMessage(@RequestBody DialogUserIdSendPostRequest message,
                                         @PathVariable("userId") String toUserId,
                                         @NonNull HttpServletRequest request,
                                         @RequestHeader(value = "x-request-id", required = false) String requestId) {
        // TODO добавить валидацию
        logger.info("Handling request with x-request-id: {}", requestId);
        try {
            // Получаем информацию о текущем пользователе
            var existId = userInterceptor.getUserIdFromToken(request);
            var sendMessage = new DialogMessage();
            sendMessage.setFrom(existId);
            sendMessage.setTo(toUserId);
            sendMessage.setText(message.getText());
            boolean response = false;
            if (IN_MEMORY) {
                response = dialogRedisService.sendMessage(sendMessage);
            } else {
                response = dialogService.sendMessage(sendMessage, requestId);
            }
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
                                         @NonNull HttpServletRequest request,
                                         @RequestHeader(value = "x-request-id", required = false) String requestId) {
        // TODO добавить валидацию
        logger.info("Handling request with x-request-id: {}", requestId);
        try {
            // Получаем информацию о текущем пользователе
            var existUserId = userInterceptor.getUserIdFromToken(request);
            var usersIds = new DialogMessage();
            usersIds.setFrom(existUserId);
            usersIds.setTo(fromUserId);
            List<DialogMessage> response;
            if (IN_MEMORY) {
                response = dialogRedisService.getMessages(usersIds);
            } else {
                response = dialogService.getMessages(usersIds, requestId);
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginPost500Response()
                    .message("Internal Server Error")
                    .code(500)
                    .requestId("no-request-id"));
        }
    }
}
