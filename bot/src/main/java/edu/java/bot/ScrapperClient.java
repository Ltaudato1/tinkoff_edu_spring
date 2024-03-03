package edu.java.bot;

import java.net.URL;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@SuppressWarnings("MultipleStringLiterals")
public class ScrapperClient {
    private final WebClient webClient;

    public ScrapperClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public ResponseEntity<String> registerChat(Long id) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder.path("/scrapper/tg-chat/{id}").build(id))
            .retrieve()
            .toEntity(String.class)
            .block();
    }

    public ResponseEntity<String> deleteChat(Long id) {
        return webClient.delete()
            .uri(uriBuilder -> uriBuilder.path("/scrapper/tg-chat/{id}").build(id))
            .retrieve()
            .toEntity(String.class)
            .block();
    }

    public ResponseEntity<String> getLinks(Long tgChatId) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/scrapper/links").build())
            .header("Tg-Chat-Id", String.valueOf(tgChatId))
            .retrieve()
            .toEntity(String.class)
            .block();
    }

    public ResponseEntity<String> addLink(Long tgChatId, URL addingUrl) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder.path("/scrapper/links").build())
            .header("Tg-Chat-Id", String.valueOf(tgChatId))
            .body(BodyInserters.fromValue(addingUrl))
            .retrieve()
            .toEntity(String.class)
            .block();
    }

}
