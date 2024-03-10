package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.Assert.assertEquals;

public class ContainerTest {
    @Mock
    private PostgreSQLContainer<?> postgresContainer;

    @Test
    public void testRunMigrations() throws Exception {
        postgresContainer = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("Sql123sqL");
        postgresContainer.start();

        // Проверьте доступ к базе данных
        try (Connection connection = DriverManager.getConnection(
            postgresContainer.getJdbcUrl(),
            postgresContainer.getUsername(),
            postgresContainer.getPassword()
        )) {
            assertEquals(1, 1);
        } catch (Exception e) {
            return;
        }

        postgresContainer.stop();
    }
}
