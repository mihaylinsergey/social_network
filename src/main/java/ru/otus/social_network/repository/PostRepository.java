package ru.otus.social_network.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.api.v1.model.Post;
import java.sql.PreparedStatement;
import java.util.UUID;

@AllArgsConstructor
@Repository
public class PostRepository {

    private final JdbcTemplate jdbc;

    public UUID createPost(Post post) {
        UUID id = UUID.randomUUID();
        String sql = "INSERT INTO posts (id, text, author_user_id) VALUES (?, ?, ?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, id);
            ps.setString(2, post.getText());
            ps.setObject(3, UUID.fromString(post.getAuthorUserId()));
            return ps;
        });
        return id;
    }
}
