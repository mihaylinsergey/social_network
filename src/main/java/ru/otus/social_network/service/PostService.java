package ru.otus.social_network.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.api.v1.model.Post;
import ru.otus.social_network.exception.InvalidDataException;
import ru.otus.social_network.repository.PostRepository;
import java.util.UUID;

@Service
public class PostService {
    private final Counter successfulInsertsCounter;
    private final PostRepository postRepository;

    public PostService(MeterRegistry meterRegistry, PostRepository postRepository) {
        this.successfulInsertsCounter = meterRegistry.counter("successful_inserts_total");
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = false)
    public UUID createPost(Post post) throws InvalidDataException {
            var postId = postRepository.createPost(post);
            if (post != null) {
                successfulInsertsCounter.increment(1.0);
            }
        return postId;
    }
}
