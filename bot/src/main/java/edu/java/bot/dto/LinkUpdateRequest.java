package edu.java.bot.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LinkUpdateRequest {
    private String url;
    private List<Long> tgChatIds;
}
