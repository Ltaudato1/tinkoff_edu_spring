package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.GitHubClient;
import edu.java.clients.GitHubEvent;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WireMockTest
public class GitHubClientIntegrationTest {
    private static GitHubClient gitHubClient;
    private static WireMockServer wireMockServer;

    @LocalServerPort
    private static int port;

    @Test
    void testGitHubClientErrorHandling() {
        // Несуществующий репозиторий
        assertThrows(WebClientResponseException.NotFound.class, () ->
            gitHubClient.getRepositoryEvents("non-existent_user", "non-existent_repo",
                OffsetDateTime.parse("2024-02-24T00:00:00Z"), OffsetDateTime.parse("2024-02-25T00:00:00Z")
            ));
    }
}
