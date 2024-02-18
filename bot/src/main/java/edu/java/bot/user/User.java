package edu.java.bot.user;

import java.net.URL;
import java.util.List;

public record User(long chatId, List<URL> trackedList) {
    public void removeLink(URL url) {
        trackedList.remove(url);
    }

    public void addLink(URL url) {
        trackedList.add(url);
    }
}
