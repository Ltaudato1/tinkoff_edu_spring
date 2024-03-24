package edu.java.domain.jpa;

import edu.java.dto.LinkResponse;
import edu.java.dto.LinkUpdateRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

public class LinksJpaDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public LinkResponse add(LinkUpdateRequest linkRequest) {
        String url = linkRequest.getUrl();
        OffsetDateTime now = OffsetDateTime.now();

        String sql = "INSERT INTO links (url, last_update_time) VALUES (:url, :updTime)";
        entityManager.createNativeQuery(sql)
            .setParameter("url", url)
            .setParameter("updTime", now)
            .executeUpdate();

        Long generatedId = (Long) entityManager.createNativeQuery("SELECT lastval()").getSingleResult();

        for (Long id : linkRequest.getTgChatIds()) {
            addLinkToChat(generatedId, id);
        }

        return new LinkResponse(generatedId, url, now);
    }

    private void addLinkToChat(long linkId, long chatId) {
        String sql = "INSERT INTO links_chats (link_id, chat_id) VALUES (:linkId, chatId)";
        entityManager.createNativeQuery(sql)
            .setParameter("linkId", linkId)
            .setParameter("chatId", chatId)
            .executeUpdate();
    }

    @Transactional
    public void remove(long id) {
        String sql1 = "DELETE FROM links WHERE chat_id = :lId";
        entityManager.createNativeQuery(sql1)
            .setParameter("lId", id)
            .executeUpdate();

        String sql2 = "DELETE FROM links_chats WHERE link_id = :link_id";
        entityManager.createNativeQuery(sql2)
            .setParameter("link_id", id)
            .executeUpdate();
    }

    @Transactional
    public List<LinkResponse> findAll() {
        List<Object[]> results =
            entityManager.createQuery("SELECT l.id, l.url, l.lastUpdateTime FROM Link l", Object[].class)
                .getResultList();

        return results.stream()
            .map(row -> new LinkResponse((Long) row[0], (String) row[1], (OffsetDateTime) row[2]))
            .collect(Collectors.toList());
    }
}
