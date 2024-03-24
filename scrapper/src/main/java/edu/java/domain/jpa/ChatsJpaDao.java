package edu.java.domain.jpa;

import edu.java.dto.ChatResponse;
import edu.java.dto.RegisterChatRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

public class ChatsJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ChatResponse add(RegisterChatRequest chatRequest) {
        Long chatId = chatRequest.getId();

        String sql = "INSERT INTO chats (chat_id) VALUES (:chatId)";
        entityManager.createNativeQuery(sql)
            .setParameter("chatId", chatId)
            .executeUpdate();

        return new ChatResponse(chatId);
    }

    @Transactional
    public void remove(long id) {
        String sql = "DELETE FROM chats WHERE chat_id = :chId";
        entityManager.createNativeQuery(sql)
            .setParameter("chId", id)
            .executeUpdate();
    }

    @Transactional
    public List<ChatResponse> findAll() {
        String sql = "SELECT chat_id FROM chats";
        List<Long> chats = entityManager.createNativeQuery(sql, Long.class).getResultList();

        return chats.stream().map(ChatResponse::new).collect(Collectors.toList());
    }
}
