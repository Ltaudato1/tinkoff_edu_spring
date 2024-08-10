package edu.java.configuration;

import edu.java.clients.github.GitHubClient;
import edu.java.clients.stackoverflow.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    public GitHubClient gitHubClient(WebClient.Builder webClientBuilder, String baseUrl) {
        return new GitHubClient(webClientBuilder.baseUrl(baseUrl).build());
    }

    @Bean
    public GitHubClient gitHubClient(WebClient.Builder webClientBuilder) {
        return new GitHubClient(webClientBuilder.baseUrl("https://api.github.com").build());
    }

    @Bean
    public StackOverflowClient stackOverflowClient(WebClient.Builder webClientBuilder, String baseUrl) {
        return new StackOverflowClient(webClientBuilder.baseUrl(baseUrl).build());
    }

    @Bean
    public StackOverflowClient stackOverflowClient(WebClient.Builder webClientBuilder) {
        return new StackOverflowClient(webClientBuilder.baseUrl("https://api.stackexchange.com/2.3").build());
    }
}
