package ru.otus.social_network.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import ru.otus.api.v1.model.Post;
import ru.otus.social_network.exception.InvalidDataException;
import ru.otus.social_network.repository.PostRepository;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PostService {
    private final Counter successfulInsertsCounter;
    private AtomicInteger insertedCount = new AtomicInteger();
    private final PostRepository postRepository;

    public PostService(MeterRegistry meterRegistry, PostRepository postRepository) {
        this.successfulInsertsCounter = meterRegistry.counter("successful_inserts_total");
        this.postRepository = postRepository;
    }

    public UUID createPost(Post post) throws InvalidDataException {
            var postId = postRepository.createPost(post);
            if (post != null) {
                insertedCount.incrementAndGet();
            }
        successfulInsertsCounter.increment(insertedCount.doubleValue());
        return postId;
    }
}
