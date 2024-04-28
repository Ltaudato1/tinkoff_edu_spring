package edu.java.clients.github;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient {
    private final WebClient webClient;

    public GitHubClient(WebClient webClient) {
        this.webClient = webClient;
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
