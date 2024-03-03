package edu.java.bot;

import edu.java.bot.dto.LinkUpdateRequest;
import edu.java.bot.dto.exceptions.ChatNotExistsException;
import edu.java.bot.dto.exceptions.DuplicateUpdateException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
public class BotController {

    private final Set<Long> processedUpdates = new HashSet<>();

    @PostMapping
    public ResponseEntity<Object> processUpdate(@RequestBody LinkUpdateRequest linkUpdate) {
        if (linkUpdate.getTgChatIds() == null || linkUpdate.getTgChatIds().isEmpty()) {
            throw new ChatNotExistsException("Chat doesn't exist");
        }

        if (processedUpdates.contains(linkUpdate.getId())) {
            throw new DuplicateUpdateException("This update has already processed");
        }

        try {
            // Какая-то логика обработки обновления
            return ResponseEntity.ok("Update was processed");
        } catch (Exception e) {
            throw e;
        }
    }
}
