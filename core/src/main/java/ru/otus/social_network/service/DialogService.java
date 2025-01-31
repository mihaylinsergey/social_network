package ru.otus.social_network.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.social_network.model.DialogMessage;

import java.util.List;

@Service
public class DialogService {

    private final RestTemplate restTemplate;

    @Value("${dialog.service.url}")
    private String URL;

    private String SEND_MESSAGE_URI = "/dialog/send";
    private String GET_MESSAGES_URI = "/dialog/get";

    private static final String REQUEST_ID_HEADER = "x-request-id";

    public DialogService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean sendMessage(DialogMessage message, String requestId) {
        // TODO добавить обработку исключений
        String endpoint = URL + SEND_MESSAGE_URI;

        // Создаем HttpHeaders и добавляем заголовок x-request-id
        HttpHeaders headers = new HttpHeaders();
        headers.set(REQUEST_ID_HEADER, requestId);

        // Создаем HttpEntity с заголовками и телом запроса
        HttpEntity<DialogMessage> request = new HttpEntity<>(message, headers);

        // Отправляем запрос
        ResponseEntity<Boolean> response = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                request,
                Boolean.class
        );
        return response.getBody() != null && response.getBody();
    }

    public List<DialogMessage> getMessages(DialogMessage usersIds, String requestId) {
        // TODO добавить обработку исключений
        String endpoint = URL + GET_MESSAGES_URI;
        // Создаем HttpHeaders и добавляем заголовок x-request-id
        HttpHeaders headers = new HttpHeaders();
        headers.set(REQUEST_ID_HEADER, requestId);

        // Создаем HttpEntity с заголовками и телом запроса
        HttpEntity<DialogMessage> request = new HttpEntity<>(usersIds, headers);

        // Отправляем запрос
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
