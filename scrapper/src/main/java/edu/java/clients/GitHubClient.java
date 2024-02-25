package edu.java.clients;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GitHubClient {
    private final WebClient webClient;

    public GitHubClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public List<GitHubEvent> getRepositoryEvents(
        String owner,
        String repo,
        OffsetDateTime since,
        OffsetDateTime until
    ) {
        return webClient.get()
            .uri("/repos/{owner}/{repo}/events?since={since}&until={until}", owner, repo, since, until)
            .retrieve()
            .bodyToFlux(GitHubEvent.class)
            .collectList()
            .block();
    }

}
