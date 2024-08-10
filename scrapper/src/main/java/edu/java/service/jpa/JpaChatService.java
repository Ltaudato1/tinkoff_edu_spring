package edu.java.service.jpa;

import edu.java.domain.jpa.ChatsJpaDao;
import edu.java.dto.ChatResponse;
import edu.java.dto.RegisterChatRequest;
import edu.java.service.ChatService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class JpaChatService implements ChatService {

    private final ChatsJpaDao jpaDao;

    @Autowired
    public JpaChatService(ChatsJpaDao jpaDao) {
        this.jpaDao = jpaDao;
    }

    @Override
    public ChatResponse registerChat(RegisterChatRequest chatRequest) {
        return jpaDao.add(chatRequest);
    }

    @Override
    public void deleteChat(long id) {
        jpaDao.remove(id);
    }

    @Override
    public List<ChatResponse> getAllChats() {
        return jpaDao.findAll();
    }
}
