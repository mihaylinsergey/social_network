package ru.otus.dialog_service.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dialog_service.model.DialogMessage;
import ru.otus.dialog_service.repository.DialogRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@AllArgsConstructor
public class DialogService {

    private final DialogRepository dialogRepository;

    public boolean saveMessage(DialogMessage message) {
        return dialogRepository.saveMessage(message);
    }

    public List<DialogMessage> getListMessages(DialogMessage message) {
        //TODO добавить обработку ошибок
        return dialogRepository.getListMessages(message);
    }
}
