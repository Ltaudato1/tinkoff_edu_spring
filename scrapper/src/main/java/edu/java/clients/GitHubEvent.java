package edu.java.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Getter;

public class GitHubEvent {
    @JsonProperty("id")
    @Getter private String id;

    @JsonProperty("type")
    @Getter private String type;

    @JsonProperty("created_at")
    @Getter private OffsetDateTime createdAt;
}
