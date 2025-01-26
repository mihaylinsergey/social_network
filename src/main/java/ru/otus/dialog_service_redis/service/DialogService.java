package ru.otus.dialog_service_redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import ru.otus.dialog_service_redis.model.DialogMessage;
import java.util.List;

@Service
public class DialogService {

    @Autowired
    RedisOperations<String, String> redisOperations;

    @Autowired
    RedisScript<String> messageOperationsScript;

    public boolean saveMessage(DialogMessage message) {
        String result = redisOperations.execute(
                messageOperationsScript,
                List.of(),
                "save",
                message.getFrom(),
                message.getTo(),
                message.getText()
        );
        return "true".equals(result);
    }

    public List<DialogMessage> getListMessages(DialogMessage message) throws JsonProcessingException {
        String result = redisOperations.execute(
                messageOperationsScript,
                List.of(),
                "get",
                message.getFrom(),
                message.getTo()
        );
        System.out.println("Fetched result: " + result);

        // Удаляем экранирование
        if (result.startsWith("\"") && result.endsWith("\"")) {
            result = result.substring(1, result.length() - 1);
        }
        result = result.replace("\\\"", "\"");

        // Десериализация JSON в список объектов
        return new ObjectMapper().readValue(result, new TypeReference<List<DialogMessage>>() {});
    }
}
