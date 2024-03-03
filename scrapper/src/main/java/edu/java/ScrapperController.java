package edu.java;

import edu.java.dto.AddLinkRequest;
import java.net.URL;
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
    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<String> registerChat(@PathVariable Long id) {
        try {
            // Логика регистрации чата
            return ResponseEntity.ok("Chat was successfully registered");
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<String> deleteChat(@PathVariable Long id) {
        // Проверка на существование чата
        try {
            // Логика удаления чата
            return ResponseEntity.ok("Chat was successfully deleted");
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/links")
    public ResponseEntity<String> getLinks(@RequestHeader("Tg-Chat-Id") Long tgChatId) {
        try {
            // Логика доставания ссылок из БД
            return ResponseEntity.ok("links were successfully got");
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/links")
    public ResponseEntity<String> addLink(
        @RequestHeader("Tg-Chat-Id") Long tgChatId,
        @RequestBody URL url
    ) {
        try {
            AddLinkRequest addLinkRequest = new AddLinkRequest(url);
            // Логика добавления ссылки
            return ResponseEntity.ok("Link was successfully added");
        } catch (Exception e) {
            throw e;
        }
    }

}
