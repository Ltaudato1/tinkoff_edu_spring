package edu.java.clients.stackoverflow;

import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/questions")
@SuppressWarnings("MultipleStringLiterals")
public class StackOverflowClient {

    private final WebClient webClient;
    private static final String MAX_ATTEMPTS = "3";

    public StackOverflowClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Retryable(retryFor = {HttpStatusCodeException.class}, maxAttemptsExpression = MAX_ATTEMPTS,
            backoff = @Backoff(delayExpression = "${retry.delay}"))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StackOverflowUpdate getQuestionUpdates(String questionId, OffsetDateTime since, OffsetDateTime until) {
        try {
            return webClient.get()
                    .uri("/questions/{questionId}/timeline?since={since}&until={until}&site=stackoverflow",
                            questionId, since, until
                    )
                    .retrieve()
                    .bodyToFlux(StackOverflowUpdate.class)
                    .collectList()
                    .block()
                    .get(0);
        } catch (HttpStatusCodeException e) {
             switch (e.getStatusCode()) {
                case HttpStatus.BAD_GATEWAY -> throw e;
                case HttpStatus.BAD_REQUEST -> throw e;
                default -> {
                    return null;
                }
             }
        }
    }

    public static String getQuestionIdFromLink(String link) {
        int startIndex = link.indexOf("/questions/") + "/questions/".length();
        int endIndex = link.indexOf("/", startIndex);
        return link.substring(startIndex, endIndex);
    }
}

