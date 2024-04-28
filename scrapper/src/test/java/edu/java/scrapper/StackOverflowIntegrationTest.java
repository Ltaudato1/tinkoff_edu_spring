package edu.java.scrapper;

import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowUpdate;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StackOverflowIntegrationTest {
    @Autowired
    private StackOverflowClient stackOverflowClient;

    @LocalServerPort
    private int port;

    @Test
    void testGetQuestionEvents() {
        // Какой-то реально существующий вопрос без ответов (только 2 события)
        String questionId = "78056675";
        OffsetDateTime since = OffsetDateTime.parse("2024-02-25T00:00:00Z");
        OffsetDateTime until = OffsetDateTime.parse("2024-02-25T19:42:00Z");

        StackOverflowUpdate events = stackOverflowClient.getQuestionUpdates(questionId, since, until);

        Assertions.assertEquals(2, events.getItems().size());
        Assertions.assertEquals("question", events.getItems().get(0).getTimelineType());
        Assertions.assertEquals(1708877124, events.getItems().get(0).getCreationDate());
    }

    @Test
    void testStackOverflowClientErrorHandling() {
        // Несуществующий вопрос
        StackOverflowUpdate events = stackOverflowClient.getQuestionUpdates("2281337",
            OffsetDateTime.parse("2024-02-24T00:00:00Z"), OffsetDateTime.parse("2024-02-25T00:00:00Z"));

        assertEquals(0, events.getItems().size());
    }
}
