package ru.otus.social_network.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.api.v1.model.DialogMessage;
import ru.otus.api.v1.model.Post;
import ru.otus.social_network.exception.InvalidDataException;
import ru.otus.social_network.repository.PostRepository;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PostService {
    private final Counter successfulInsertsCounter;
    private final PostRepository postRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private final RabbitTemplate rabbitTemplate;
    private final int POST_FEED_LIMIT = 1000;
    private final int REDIS_TTL = 60;

    public PostService(MeterRegistry meterRegistry, PostRepository postRepository, RedisTemplate<String, Object> redisTemplate, RabbitTemplate rabbitTemplate) {
        this.successfulInsertsCounter = meterRegistry.counter("successful_inserts_total");
        this.postRepository = postRepository;
        this.redisTemplate = redisTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional(readOnly = false)
    public UUID createPost(Post post) throws InvalidDataException {
        var postId = postRepository.createPost(post);
        if (post != null) {
            successfulInsertsCounter.increment(1.0);
        }
        var message = createMessage(post);
        try {
            rabbitTemplate.convertAndSend("postQueue", message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postId;
    }

    @Transactional(readOnly = false)
    public List<Post> getPostFeed(String userId) {
        // Получаем данные из кэша
        List<Post> cachedFeed = (List<Post>) redisTemplate.opsForValue().get(userId);
        if (cachedFeed != null) {
            return cachedFeed;
        }
        // Если данных нет в кэше, получаем их из БД
        List<Post> postFeed = postRepository.getPostFeed(userId, POST_FEED_LIMIT);
        // Сохранение данных в кэше с TTL 60 секунд
        redisTemplate.opsForValue().set(userId, postFeed, REDIS_TTL, TimeUnit.SECONDS);
        return postFeed;
    }

    @Transactional(readOnly = false)
    public boolean updatePost(Post post) throws InvalidDataException {
        return postRepository.updatePost(post);
    }

    @Transactional(readOnly = false)
    public boolean deletePost(Post post) throws InvalidDataException {
        return postRepository.deletePost(post);
    }

    @Transactional(readOnly = false)
    public Post getPost(Post post) throws InvalidDataException {
        return postRepository.getPost(post);
    }

    private DialogMessage createMessage(Post post) {
        DialogMessage message = new DialogMessage();
        message.setText(post.getText());
        message.setFrom(post.getAuthorUserId());
        return message;
    }
}
