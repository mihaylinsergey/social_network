package ru.otus.dialog_service.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.dialog_service.model.DialogMessage;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class DialogRepository {

    private final JdbcTemplate jdbc;

    public boolean saveMessage(DialogMessage message) {
        String sql = "INSERT INTO dialog_messages (from_user_id, to_user_id, text) VALUES (?, ?, ?)";
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, UUID.fromString(message.getFrom()));
            ps.setObject(2, UUID.fromString(message.getTo()));
            ps.setString(3, message.getText());
            return ps;
        });
        return true;
    }

    public List<DialogMessage> getListMessages(DialogMessage usersIds) {
        String sql = """
                SELECT from_user_id, to_user_id, text
                FROM dialog_messages
                WHERE from_user_id = ?
                AND to_user_id = ?
                ORDER BY created_at DESC
                """;
        return jdbc.query(sql, this::mapRowToDialogMessage,
                UUID.fromString(usersIds.getFrom()),
                UUID.fromString(usersIds.getTo()));
    }

    private DialogMessage mapRowToDialogMessage(java.sql.ResultSet rs, int rowNum) throws SQLException {
        DialogMessage message = new DialogMessage();
        message.setFrom(String.valueOf(rs.getObject("from_user_id", UUID.class)));
        message.setTo(String.valueOf(rs.getObject("to_user_id", UUID.class)));
        message.setText(rs.getString("text"));
        return message;
    }
}
