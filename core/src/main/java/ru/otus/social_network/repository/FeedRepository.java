package ru.otus.social_network.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.social_network.model.DialogMessage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class FeedRepository {

    private final JdbcTemplate jdbc;

    public boolean createMessage(DialogMessage message) {
        String sql = "INSERT INTO feeds (user_id, friend_id, text) VALUES (?, ?, ?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, UUID.fromString(message.getFrom()));
            ps.setObject(2, UUID.fromString(message.getTo()));
            ps.setString(3, message.getText());
            return ps;
        });
        return true;
    }

    public List<DialogMessage> getFeedById(String userId, int limit) {
        String sql = """
                SELECT user_id, friend_id, text
                FROM feeds
                WHERE user_id = ?
                ORDER BY created_at DESC
                LIMIT ?
                """;
        return jdbc.query(sql, this::mapRowToMessage, UUID.fromString(userId), limit);
    }

    private DialogMessage mapRowToMessage(java.sql.ResultSet rs, int rowNum) throws SQLException {
        DialogMessage message = new DialogMessage();
        message.setFrom(String.valueOf(rs.getObject("user_id", UUID.class)));
        message.setTo(String.valueOf(rs.getObject("friend_id", UUID.class)));
        message.setText(rs.getString("text"));
        return message;
    }
}
