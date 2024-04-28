package edu.java.domain;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;

@AllArgsConstructor
public class LinksJdbcDao {
    private final JdbcTemplate jdbcTemplate;

    private static final int MINUTES_TO_SECONDS = 60;
    private static final int CHECK_INTERVAL = 10;

    public void add(String url, List<Long> chatIds) {
        String sql = "INSERT INTO links (url, last_update_time) VALUES (?, ?)";
        jdbcTemplate.update(sql, url, OffsetDateTime.now());

        Long id = jdbcTemplate.queryForObject("SELECT lastval()", Long.class);

        for (Long chatId : chatIds) {
            addLinkToChat(id, chatId);
        }
    }

    public void remove(long id) {
        removeLinkFromChats(id);
        String sql = "DELETE FROM links WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private void addLinkToChat(long linkId, long chatId) {
        String sql = "INSERT INTO links_chats (link_id, chat_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, linkId, chatId);
    }

    private void removeLinkFromChats(long linkId) {
        String sql = "DELETE FROM links_chats WHERE link_id = ?";
        jdbcTemplate.update(sql, linkId);
    }

    public Map<Long, Pair<String, OffsetDateTime>> findAll() {
        String sql =
            "SELECT lc.chat_id, l.url, l.last_update_time FROM links_chats lc INNER JOIN links l ON lc.link_id = l.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("chat_id");
            String url = rs.getString("url");

            Timestamp timestamp = rs.getTimestamp("last_update_time");
            Instant instant = timestamp.toInstant();
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(timestamp.getTimezoneOffset() * MINUTES_TO_SECONDS);
            OffsetDateTime lastUpdateTime = OffsetDateTime.ofInstant(instant, offset);

            return Map.entry(id, Pair.of(url, lastUpdateTime));
        }).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Long, Pair<String, OffsetDateTime>> findStale() {
        String sql = "SELECT lc.chat_id, l.url, l.last_update_time FROM links_chats lc " +
            "INNER JOIN links l ON lc.link_id = l.id " +
            "WHERE l.last_update_time <= NOW() - INTERVAL '10' MINUTE";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("chat_id");
            String url = rs.getString("url");

            Timestamp timestamp = rs.getTimestamp("last_update_time");
            Instant instant = timestamp.toInstant();
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(timestamp.getTimezoneOffset() * MINUTES_TO_SECONDS);
            OffsetDateTime lastUpdateTime = OffsetDateTime.ofInstant(instant, offset);

            return Map.entry(id, Pair.of(url, lastUpdateTime));
        }).stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public int update(Long linkId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        String sql = "UPDATE links SET last_update_time = ? WHERE id = ?";
        return jdbcTemplate.update(sql, currentTime, linkId);
    }
}
