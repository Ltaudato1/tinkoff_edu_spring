package edu.java.service.jdbc;

import edu.java.domain.ChatsJdbcDao;
import edu.java.dto.ChatResponse;
import edu.java.dto.RegisterChatRequest;
import edu.java.service.ChatService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcChatService implements ChatService {
    private final ChatsJdbcDao chatsJdbcDao;

    @Autowired
    public JdbcChatService(ChatsJdbcDao chatsJdbcDao) {
        this.chatsJdbcDao = chatsJdbcDao;
    }

    @Override
    public ChatResponse registerChat(RegisterChatRequest chatRequest) {
        return chatsJdbcDao.add(chatRequest);
    }

    @Override
    public void deleteChat(long id) {
        chatsJdbcDao.remove(id);
    }

    @Override
    public List<ChatResponse> getAllChats() {
        return chatsJdbcDao.findAll();
    }
}
