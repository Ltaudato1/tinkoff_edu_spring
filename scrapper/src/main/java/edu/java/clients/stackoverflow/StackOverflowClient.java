package edu.java.clients.stackoverflow;

import java.time.OffsetDateTime;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClient {

    private final WebClient webClient;

    public StackOverflowClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public StackOverflowUpdate getQuestionUpdates(String questionId, OffsetDateTime since, OffsetDateTime until) {
        return webClient.get()
                .uri("/questions/{questionId}/timeline?since={since}&until={until}&site=stackoverflow",
                        questionId, since, until
                )
                .retrieve()
                .bodyToFlux(StackOverflowUpdate.class)
                .collectList()
                .block()
                .get(0);
    }
}

