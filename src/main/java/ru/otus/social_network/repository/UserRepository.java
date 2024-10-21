package ru.otus.social_network.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.api.v1.model.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UUID save(User user) {
        UUID id = UUID.randomUUID();
        String sql = "INSERT INTO users (id, first_name, second_name, birthdate, biography, city) VALUES (?, ?, ?, ?, ?, ?)";

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, id);
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getSecondName());
            ps.setDate(4, java.sql.Date.valueOf(user.getBirthdate()));
            ps.setString(5, user.getBiography());
            ps.setString(6, user.getCity());
            return ps;
        });
        return id;
    }

    public Optional<User> findUserById(String id) {
        String sql = """
                SELECT id, first_name, second_name, birthdate, biography, city
                FROM users
                WHERE id = ?
                """;
        User user = jdbc.queryForObject(sql, this::mapRowToUser, UUID.fromString(id));
        return Optional.ofNullable(user);
    }

    public Collection<User> findUserByFirstAndLastName(String firstName, String secondName) {
        String sql = """
                SELECT id, first_name, second_name, birthdate, biography, city
                FROM users
                WHERE first_name LIKE ? AND second_name LIKE ?
                ORDER BY id
                """;
        return jdbc.query(sql, this::mapRowToUser, firstName + "%", secondName + "%");
    }

    private User mapRowToUser(java.sql.ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(String.valueOf(rs.getObject("id", UUID.class)));
        user.setFirstName(rs.getString("first_name"));
        user.setSecondName(rs.getString("second_name"));
        user.setBirthdate(dateOrNull(rs.getString("birthdate")));
        user.setBiography(rs.getString("biography"));
        user.setCity(rs.getString("city"));
        return user;
    }

    private static LocalDate dateOrNull(String date) {
        return date == null ? null : LocalDate.parse(date);
    }
}
