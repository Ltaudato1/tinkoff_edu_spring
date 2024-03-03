package edu.java.bot.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LinkUpdateRequest {
    private Long id;
    private String url;
    private String description;
    private List<Long> tgChatIds;
}
