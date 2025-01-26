package ru.otus.social_network.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class FriendRepository {

    private final JdbcTemplate jdbc;

    public boolean setFriendById(String userId, String friendId) {
        String sql = "INSERT INTO friends (target_user, friend) VALUES (?, ?)";
        int rowsAffected = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, UUID.fromString(userId));
            ps.setObject(2, UUID.fromString(friendId));
            return ps;
        });
        return rowsAffected > 0;
    }

    public boolean deleteFriendById(String userId, String friendId) {
        String sql = """
        DELETE FROM friends
         WHERE target_user = ? AND  friend = ?
        """;
        int rowsAffected = jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, UUID.fromString(userId));
            ps.setObject(2, UUID.fromString(friendId));
            return ps;
        });
        return rowsAffected > 0;
    }

    public List<UUID> getFriendsById(String userId) {
        String sql = """
                SELECT friend
                FROM friends
                WHERE target_user = ?
                """;
        RowMapper<UUID> rowMapper = (rs, rowNum) -> UUID.fromString(rs.getString("friend"));
        List<UUID> listFriends = jdbc.query(sql, rowMapper, UUID.fromString(userId));
        return listFriends;
    }
}
