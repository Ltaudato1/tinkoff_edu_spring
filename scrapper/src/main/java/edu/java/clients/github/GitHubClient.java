package edu.java.clients.github;

import java.time.OffsetDateTime;
import java.util.List;
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
@RequestMapping("/api/repos")
@SuppressWarnings("MultipleStringLiterals")
public class GitHubClient {
    private final WebClient webClient;
    private static final String MAX_ATTEMPTS = "3";

    public GitHubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Retryable(retryFor = {HttpStatusCodeException.class},
            maxAttemptsExpression = MAX_ATTEMPTS,
            backoff = @Backoff(delayExpression = "${retry.delay}"))
    public List<GitHubEvent> getRepositoryEvents(
            String owner,
            String repo,
            OffsetDateTime since,
            OffsetDateTime until
    ) {
        try {
            return webClient.get()
                    .uri("/api/repos/{owner}/{repo}/events?since={since}&until={until}", owner, repo, since, until)
                    .retrieve()
                    .bodyToFlux(GitHubEvent.class)
                    .collectList().block();
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

    public static String getOwnerFromLink(String link) {
        int startIndex = link.indexOf("/repos/") + "/repos/".length();
        int endIndex = link.indexOf("/", startIndex);
        return link.substring(startIndex, endIndex);
    }

    public static String getRepoFromLink(String link) {
        int startIndex = link.indexOf("/repos/") + "/repos/".length();
        int endIndex = link.indexOf("/events", startIndex);
        return link.substring(endIndex + 1);
    }

}
