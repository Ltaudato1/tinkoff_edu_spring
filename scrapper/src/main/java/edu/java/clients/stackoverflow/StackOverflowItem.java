package edu.java.clients.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class StackOverflowItem {
    @JsonProperty("timeline_type")
    @Getter private String timelineType;

    @JsonProperty("creation_date")
    @Getter private long creationDate;
}
