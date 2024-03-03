package edu.java.clients;

import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RestController
@RequestMapping("/api/repos")
public class GitHubClient {
    private final WebClient webClient;

    public GitHubClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GitHubEvent> getRepositoryEvents(
        String owner,
        String repo,
        OffsetDateTime since,
        OffsetDateTime until
    ) {
        return webClient.get()
            .uri("/api/repos/{owner}/{repo}/events?since={since}&until={until}", owner, repo, since, until)
            .retrieve()
            .bodyToFlux(GitHubEvent.class)
            .collectList()
            .block();
    }

}
