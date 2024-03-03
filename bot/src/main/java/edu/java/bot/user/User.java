package edu.java.bot.user;

import java.net.URL;
import java.util.List;

public class User {
    private final List<URL> trackedList;

    public User(List<URL> trackedList) {
        this.trackedList = trackedList;
    }

    public void removeLink(URL url) {
        trackedList.remove(url);
    }

    public void addLink(URL url) {
        trackedList.add(url);
    }

    public List<URL> trackedList() {
        return this.trackedList;
    }
}
