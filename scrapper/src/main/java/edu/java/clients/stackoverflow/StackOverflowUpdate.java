package edu.java.clients.stackoverflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

public class StackOverflowUpdate {
    @JsonProperty("items")
    @Getter private List<StackOverflowItem> items;
}
