package edu.java.configuration;

import edu.java.clients.GitHubClient;
import edu.java.clients.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Validated
public class ClientConfiguration {
    @Bean
    public GitHubClient gitHubClient(WebClient.Builder webClientBuilder) {
        return new GitHubClient(webClientBuilder, "https://api.github.com");
    }

    @Bean
    public StackOverflowClient stackOverflowClient(WebClient.Builder webClientBuilder) {
        return new StackOverflowClient(webClientBuilder, "https://api.stackexchange.com/2.3");
    }
}
