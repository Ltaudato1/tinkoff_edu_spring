package edu.java.service;

import edu.java.dto.LinkResponse;
import edu.java.dto.LinkUpdateRequest;
import java.util.List;

public interface LinkService {
    LinkResponse addLink(LinkUpdateRequest linkRequest);

    void removeLink(long id);

    List<LinkResponse> getAllLinks();

    List<LinkResponse> getStaleLinks();
}
