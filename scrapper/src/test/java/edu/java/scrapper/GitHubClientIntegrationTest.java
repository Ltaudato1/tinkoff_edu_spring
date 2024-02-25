package edu.java.scrapper;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.GitHubClient;
import edu.java.clients.GitHubEvent;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GitHubClientIntegrationTest {
    @Autowired
    private GitHubClient gitHubClient;

    @LocalServerPort
    private int port;

    @Test
    void testGetRepositoryEvents() {
        // События в моём репозитории за 24 февраля (ок. 30)
        String owner = "Ltaudato1";
        String repo = "tinkoff_edu_spring";
        OffsetDateTime since = OffsetDateTime.parse("2024-02-24T00:00:00Z");
        OffsetDateTime until = OffsetDateTime.parse("2024-02-25T00:00:00Z");

        List<GitHubEvent> events = gitHubClient.getRepositoryEvents(owner, repo, since, until);

        Assertions.assertEquals("35972480434", events.get(0).getId());
        Assertions.assertEquals("PushEvent", events.get(0).getType());
    }

    @Test
    void testGitHubClientErrorHandling() {
        // Несуществующий репозиторий
        assertThrows(WebClientResponseException.NotFound.class, () ->
            gitHubClient.getRepositoryEvents("non-existent_user", "non-existent_repo",
                OffsetDateTime.parse("2024-02-24T00:00:00Z"), OffsetDateTime.parse("2024-02-25T00:00:00Z")
            ));
    }
}
