package edu.java;

import edu.java.dto.LinkUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class BotClient {
    private final WebClient webClient;

    public BotClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public ResponseEntity<Object> processUpdate(LinkUpdateRequest linkUpdate) {
        return webClient.post()
            .uri("/updates")
            .body(BodyInserters.fromValue(linkUpdate))
            .retrieve()
            .toEntity(Object.class)
            .block();
    }
}
