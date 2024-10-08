package edu.java.domain.jdbc;

import edu.java.dto.ChatResponse;
import edu.java.dto.RegisterChatRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@AllArgsConstructor
public class ChatsJdbcDao {
    private final JdbcTemplate jdbcTemplate;

    public ChatResponse add(RegisterChatRequest chat) {
        String sql = "INSERT INTO chats (chat_id) VALUES (?)";
        jdbcTemplate.update(sql, chat.getId());
        return new ChatResponse(chat.getId());
    }

    public void remove(long id) {
        String sql = "DELETE FROM chats WHERE chat_id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<ChatResponse> findAll() {
        String sql = "SELECT * FROM chats";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ChatResponse chat = new ChatResponse(
                rs.getLong("chat_id")
            );
            return chat;
        });
    }
}
