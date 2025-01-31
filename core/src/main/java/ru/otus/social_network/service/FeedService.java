package ru.otus.social_network.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.social_network.exception.InvalidDataException;
import ru.otus.social_network.model.DialogMessage;
import ru.otus.social_network.repository.FeedRepository;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedService {

    private static final int LIMIT_FEED = 100;

    private final FeedRepository feedRepository;

    public boolean createMessage(DialogMessage message) throws InvalidDataException {
        return feedRepository.createMessage(message);
    }

    public List<DialogMessage> getFeedById(String userId) {
        return feedRepository.getFeedById(userId, LIMIT_FEED);
    }
}
