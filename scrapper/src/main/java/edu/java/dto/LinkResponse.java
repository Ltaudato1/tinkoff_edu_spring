package edu.java.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LinkResponse {
    private Long id;
    private String url;
    private OffsetDateTime lastUpdateTime;
}
