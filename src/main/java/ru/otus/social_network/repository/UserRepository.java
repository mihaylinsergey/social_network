package ru.otus.social_network.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import ru.otus.api.v1.model.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UUID save(User user) {
        var id = UUID.randomUUID();
        jdbc.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(
                                        "insert into users (id, first_name, second_name, birthdate, biography, city) values (?, ?, ?, ?, ?, ?)",
                                        new String[]{"id"});
                        ps.setObject(1, id);
                        ps.setString(2, user.getFirstName());
                        ps.setString(3, user.getSecondName());
                        ps.setDate(4, java.sql.Date.valueOf(user.getBirthdate()));
                        ps.setString(5, user.getBiography());
                        ps.setString(6, user.getCity());
                        return ps;
                    }
                });
        return id;
    }

    public Optional<User> findUserById(String id) {
        User rsl = jdbc.queryForObject(""" 
                                select id, first_name, second_name, birthdate, biography, city 
                                from users
                                where id = ?
                                """,
                (rs, rowNum) -> {
                        User user = new User();
                        user.setId(String.valueOf(rs.getObject("id", UUID.class)));
                        user.setFirstName(rs.getString("first_name"));
                        user.setSecondName(rs.getString("second_name"));
                        user.setBirthdate(dateOrNull(rs.getString("birthdate")));
                        user.setBiography(rs.getString("biography"));
                        user.setCity(rs.getString("city"));
                        return user;
                },
                UUID.fromString(id));
        return Optional.ofNullable(rsl);
    }

    private static LocalDate dateOrNull(String date) {
        return date == null ? null : LocalDate.parse(date);
    }


}
