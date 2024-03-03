package edu.java.scrapper;

import edu.java.clients.StackOverflowClient;
import edu.java.clients.StackOverflowUpdate;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StackOverflowIntegrationTest {
    @Autowired
    private StackOverflowClient stackOverflowClient;

    @LocalServerPort
    private int port;

    @Test
    void testStackOverflowClientErrorHandling() {
        // Несуществующий вопрос
        StackOverflowUpdate events = stackOverflowClient.getQuestionUpdates("2281337",
            OffsetDateTime.parse("2024-02-24T00:00:00Z"), OffsetDateTime.parse("2024-02-25T00:00:00Z"));

        assertEquals(0, events.getItems().size());
    }
}
