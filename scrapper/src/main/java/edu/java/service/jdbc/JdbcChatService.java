package edu.java.service.jdbc;

import edu.java.domain.ChatsJdbcDao;
import edu.java.dto.ChatResponse;
import edu.java.dto.RegisterChatRequest;
import edu.java.service.ChatService;
import java.util.ArrayList;
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
    public void registerChat(RegisterChatRequest chatRequest) {
        chatsJdbcDao.add(chatRequest.getId());
    }

    @Override
    public void deleteChat(long id) {
        chatsJdbcDao.remove(id);
    }

    @Override
    public List<ChatResponse> getAllChats() {
        List<Long> list = chatsJdbcDao.findAll();
        List<ChatResponse> result = new ArrayList<>();
        for (Long id : list) {
            result.add(new ChatResponse(id));
        }
        return result;
    }
}
