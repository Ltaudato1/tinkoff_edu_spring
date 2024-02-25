package edu.java.clients;

import java.time.OffsetDateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StackOverflowClient {

    private final WebClient webClient;

    public StackOverflowClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
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

