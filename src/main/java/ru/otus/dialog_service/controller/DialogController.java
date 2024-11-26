package ru.otus.dialog_service.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.dialog_service.model.DialogMessage;
import ru.otus.dialog_service.service.DialogService;

@AllArgsConstructor
@RequestMapping("/dialog")
@RestController
public class DialogController {

    private final DialogService dialogService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody DialogMessage message) {
        // TODO добавить валидацию и обработку исключений
            var response = dialogService.saveMessage(message);
            return ResponseEntity.ok(response);
    }


    @PostMapping("/get")
    public ResponseEntity<?> getMessages(@RequestBody DialogMessage message) {
        // TODO добавить валидацию и обработку исключений
        var response = dialogService.getListMessages(message);
        return ResponseEntity.ok(response);
    }
}
