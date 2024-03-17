package edu.java;

import edu.java.dto.LinkUpdateRequest;
import edu.java.dto.RegisterChatRequest;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import java.net.URL;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scrapper")
public class ScrapperController {
    private final LinkService linkService;
    private final ChatService chatService;

    @Autowired
    public ScrapperController(LinkService linkService, ChatService chatService) {
        this.linkService = linkService;
        this.chatService = chatService;
    }

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<String> registerChat(@RequestBody Long id) {
        try {
            chatService.registerChat(new RegisterChatRequest(id));
            return ResponseEntity.ok("Chat was successfully registered");
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<String> deleteChat(@PathVariable Long id) {
        try {
            chatService.deleteChat(id);
            return ResponseEntity.ok("Chat was successfully deleted");
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/links")
    public ResponseEntity<String> getAllLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) {
        try {
            linkService.getAllLinks();
            return ResponseEntity.ok("links were successfully got");
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/links")
    public ResponseEntity<String> addLink(
        @RequestHeader("Tg-Chat-Id") List<Long> tgChatIds,
        @RequestBody URL url
    ) {
        try {
            LinkUpdateRequest addLinkRequest = new LinkUpdateRequest(url.toString(), tgChatIds);
            linkService.addLink(addLinkRequest);
            return ResponseEntity.ok("Link was successfully added");
        } catch (Exception e) {
            throw e;
        }
    }

}
