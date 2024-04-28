package edu.java.domain;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

public class ChatsJdbcDao {
    private final JdbcTemplate jdbcTemplate;

    public ChatsJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Long id) {
        String sql = "INSERT INTO chats (chat_id) VALUES (?)";
        jdbcTemplate.update(sql, id);
    }

    public void remove(long id) {
        String sql = "DELETE FROM chats WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Long> findAll() {
        String sql = "SELECT * FROM chats";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("chat_id"));
    }
}
