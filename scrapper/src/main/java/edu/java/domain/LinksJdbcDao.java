package edu.java.domain;

import edu.java.dto.LinkResponse;
import edu.java.dto.LinkUpdateRequest;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@AllArgsConstructor
public class LinksJdbcDao {
    private final JdbcTemplate jdbcTemplate;

    private static final int MINUTES_TO_SECONDS = 60;

    public LinkResponse add(LinkUpdateRequest link) {
        String sql = "INSERT INTO links (url, last_update_time) VALUES (?, ?)";
        jdbcTemplate.update(sql, link.getUrl(), OffsetDateTime.now());

        Long id = jdbcTemplate.queryForObject("SELECT lastval()", Long.class);
        LinkResponse createdLink = new LinkResponse(id, link.getUrl(), OffsetDateTime.now());

        for (Long chatId : link.getTgChatIds()) {
            addLinkToChat(id, chatId);
        }

        return createdLink;
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

    public List<LinkResponse> findAll() {
        String sql =
            "SELECT lc.chat_id, l.url, l.last_update_time FROM links_chats lc INNER JOIN links l ON lc.link_id = l.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("chat_id");
            String url = rs.getString("url");
            Timestamp timestamp = rs.getTimestamp("last_update_time");
            Instant instant = timestamp.toInstant();
            ZoneOffset offset = ZoneOffset.ofTotalSeconds(timestamp.getTimezoneOffset() * MINUTES_TO_SECONDS);
            OffsetDateTime lastUpdateTime = OffsetDateTime.ofInstant(instant, offset);
            return new LinkResponse(id, url, lastUpdateTime);
        });
    }

}
