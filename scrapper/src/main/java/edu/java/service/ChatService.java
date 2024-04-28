package edu.java.service;

import edu.java.dto.ChatResponse;
import edu.java.dto.RegisterChatRequest;
import java.util.List;

public interface ChatService {
    void registerChat(RegisterChatRequest chatRequest);

    void deleteChat(long id);

    List<ChatResponse> getAllChats();
}
