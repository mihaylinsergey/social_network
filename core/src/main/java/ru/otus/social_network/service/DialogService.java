package ru.otus.social_network.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.api.v1.model.DialogMessage;

import java.util.List;

@Service
public class DialogService {

    private final RestTemplate restTemplate;

    @Value("${dialog.service.url}")
    private String URL;

    private String SEND_MESSAGE_URI = "/dialog/send";
    private String GET_MESSAGES_URI = "/dialog/get";

    public DialogService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean sendMessage(DialogMessage message) {
        // TODO добавить обработку исключений
            String endpoint = URL + SEND_MESSAGE_URI;
            HttpEntity<DialogMessage> request = new HttpEntity<>(message);
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    endpoint,
                    HttpMethod.POST,
                    request,
                    Boolean.class
            );
            return response.getBody() != null && response.getBody();
    }

    public List<DialogMessage> getMessages(DialogMessage usersIds) {
        // TODO добавить обработку исключений
        String endpoint = URL + GET_MESSAGES_URI;
        HttpEntity<DialogMessage> request = new HttpEntity<>(usersIds);
        ResponseEntity<List<DialogMessage>> response = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<List<DialogMessage>>() {
                }
        );
        return response.getBody();
    }
}
