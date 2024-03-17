package edu.java.clients;

import java.time.OffsetDateTime;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RestController
@RequestMapping("/api/questions")
@SuppressWarnings("MultipleStringLiterals")
public class StackOverflowClient {

    private final WebClient webClient;

    public StackOverflowClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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

    public static String getQuestionIdFromLink(String link) {
        int startIndex = link.indexOf("/questions/") + "/questions/".length();
        int endIndex = link.indexOf("/", startIndex);
        return link.substring(startIndex, endIndex);
    }
}

