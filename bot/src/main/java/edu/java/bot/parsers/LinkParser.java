package edu.java.bot.parsers;

import java.net.MalformedURLException;
import java.net.URL;

public class LinkParser {
    private LinkParser() {

    }

    public static URL stringToUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
