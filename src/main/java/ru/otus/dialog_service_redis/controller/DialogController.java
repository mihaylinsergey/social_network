package ru.otus.dialog_service_redis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.dialog_service_redis.model.DialogMessage;
import ru.otus.dialog_service_redis.service.DialogService;

@AllArgsConstructor
@Controller
@RequestMapping("/dialog")
public class DialogController {


    private final DialogService dialogService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody DialogMessage message) {
        // TODO добавить валидацию и обработку исключений
        var response = dialogService.saveMessage(message);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/get")
    public ResponseEntity<?> getMessages(@RequestBody DialogMessage message) throws JsonProcessingException {
        // TODO добавить валидацию и обработку исключений
        var response = dialogService.getListMessages(message);
        return ResponseEntity.ok(response);
    }
}
