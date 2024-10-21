package ru.otus.social_network.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.api.v1.model.Post;
import ru.otus.social_network.exception.InvalidDataException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
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

    public List<Post> getPostFeed(String userId, int limit) {
        String sql = """
                SELECT id, text, author_user_id
                FROM posts
                JOIN friends ON posts.author_user_id = friends.friend
                WHERE friends.target_user = ?
                ORDER BY posts.created_at DESC
                LIMIT ?
                """;
        return jdbc.query(sql, this::mapRowToPost, UUID.fromString(userId), limit);
    }

    public boolean updatePost(Post post) {
        String sql = """
                UPDATE posts SET text = ?
                WHERE id = ?
                AND author_user_id = ?
                """;
        int rowsAffected = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, post.getText());
            ps.setObject(2, UUID.fromString(post.getId()));
            ps.setObject(3, UUID.fromString(post.getAuthorUserId()));
            return ps;
        });
        return rowsAffected > 0;
    }

    public boolean deletePost(Post post) {
        String sql = """
                DELETE FROM posts
                WHERE id = ?
                AND author_user_id = ?
                """;
        int rowsAffected = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, UUID.fromString(post.getId()));
            ps.setObject(2, UUID.fromString(post.getAuthorUserId()));
            return ps;
        });
        return rowsAffected > 0;
    }

    public Post getPost(Post post) throws InvalidDataException {
        String sql = """
                SELECT id, text, author_user_id
                FROM posts
                WHERE id = ?
                """;
        List<Post> listPost = jdbc.query(sql, this::mapRowToPost, UUID.fromString(post.getId()));
        if (listPost.isEmpty()) {
            throw new InvalidDataException("Post is not exists");
        }
        return listPost.get(0);
    }

    private Post mapRowToPost(java.sql.ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
        post.setId(String.valueOf(rs.getObject("id", UUID.class)));
        post.setText(rs.getString("text"));
        post.setAuthorUserId(String.valueOf(rs.getObject("author_user_id", UUID.class)));
        return post;
    }
}
