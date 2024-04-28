package edu.java.clients.github;

import java.time.OffsetDateTime;
import java.util.List;
import edu.java.clients.github.GitHubEvent;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RestController
@RequestMapping("/api/repos")
@SuppressWarnings("MultipleStringLiterals")
public class GitHubClient {
    private final WebClient webClient;

    public GitHubClient(WebClient webClient) {
        this.webClient = webClient;
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
