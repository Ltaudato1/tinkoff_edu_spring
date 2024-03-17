package edu.java.service;

public interface LinkUpdateService {
    int update(Long linkId);

    void updateStaleLinks();
}
