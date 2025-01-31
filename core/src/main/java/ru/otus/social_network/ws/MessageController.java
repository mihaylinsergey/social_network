package ru.otus.social_network.ws;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.social_network.exception.InvalidDataException;
import ru.otus.social_network.model.DialogMessage;
import ru.otus.social_network.model.UserRegisterPost200Response;
import ru.otus.social_network.service.FeedService;
import ru.otus.social_network.service.FriendService;

@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    private final FeedService feedService;

    private final FriendService friendService;

    public MessageController(SimpMessagingTemplate messagingTemplate, FeedService feedService, FriendService friendService) {
        this.messagingTemplate = messagingTemplate;
        this.feedService = feedService;
        this.friendService = friendService;
    }

    @MessageMapping("/post/feed/")
    public void processMessage(@Payload UserRegisterPost200Response user) {
        var userId = user.getUserId();
        var feeds = feedService.getFeedById(userId);
        messagingTemplate.convertAndSendToUser(
                userId,"/post/feed/posted",
                feeds);
    }

    @RabbitListener(queues = "postQueue")
    public void listen(DialogMessage message) {
        //TODO добавить обработку исключений и валидацию
        var friends = friendService.getFriendsById(message.getFrom());
        friends.forEach(user -> {
            message.setTo(String.valueOf(user));
            try {
                feedService.createMessage(message);
                messagingTemplate.convertAndSendToUser(
                        String.valueOf(user), "/post/feed/posted", message);
            } catch (InvalidDataException e) {
                //TODO добавить логгирование и обработку исключения
                throw new RuntimeException(e);
            }
        });
    }
}
