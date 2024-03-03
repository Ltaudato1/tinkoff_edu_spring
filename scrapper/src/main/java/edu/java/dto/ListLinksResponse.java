package edu.java.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ListLinksResponse {
    private List<LinkResponse> links;
    private int size;
}
