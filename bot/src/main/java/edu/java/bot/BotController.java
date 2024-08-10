package edu.java.bot;

import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.dto.exceptions.ChatNotExistsException;
import edu.java.bot.user.UserMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
public class BotController {
    @PostMapping
    public ResponseEntity<Object> processUpdate(@RequestBody LinkUpdateRequest linkUpdate) {
        if (linkUpdate.getTgChatIds() == null || linkUpdate.getTgChatIds().isEmpty()) {
            throw new ChatNotExistsException("Chat doesn't exist");
        }

        try {
            for (Long id : linkUpdate.getTgChatIds()) {
                UserMessage.updateMessage(id, linkUpdate.getUrl());
            }
            return ResponseEntity.ok("Update was processed");
        } catch (Exception e) {
            throw e;
        }
    }
}
