package edu.java.dto;

import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RemoveLinkRequest {
    private URL link;
}
