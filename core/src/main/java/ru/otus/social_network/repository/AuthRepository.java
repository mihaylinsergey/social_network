package ru.otus.social_network.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.social_network.model.Auth;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
@Repository
public class AuthRepository {
    private final JdbcTemplate jdbc;

    public void saveUserAndPassword(Auth auth) {
        jdbc.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(
                                    "insert into auths (user_id, password) values (?, ?)");
                    ps.setObject(1, auth.userId());
                    ps.setString(2, auth.password());
                    return ps;
                });
    }

    public Optional<Auth> findAuthById(String id) {
        Auth rsl = jdbc.queryForObject("select user_id, password from auths where user_id = ?",
                (rs, rowNum) -> {
                    Auth auth = new Auth(
                            rs.getObject("user_id", UUID.class),
                            rs.getString("password"));
                    return auth;
                },
                UUID.fromString(id));
        return Optional.ofNullable(rsl);
    }
}
