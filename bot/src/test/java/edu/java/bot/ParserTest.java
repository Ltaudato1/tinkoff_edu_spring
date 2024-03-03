package edu.java.bot;

import edu.java.bot.handlers.Invoker;
import edu.java.bot.parsers.CommandParser;
import edu.java.bot.parsers.LinkParser;
import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ParserTest {

    @Test
    void URLParserTestWithValidLink() throws MalformedURLException {
        String validUrl = "https://example.com";
        URL url = new URL("https://example.com");
        assertNotNull(url);
        assertEquals(url, LinkParser.stringToUrl(validUrl));
    }

    @Test
    void URLParserTestWithInvalidLink() {
        String invalidUrl = "abacaba";
        assertNull(LinkParser.stringToUrl(invalidUrl));
    }

    @Test
    void commandParserTestWithValidMessage() {
        String message = "/track https://abacaba.com";

        Invoker invoker = Mockito.mock(Invoker.class);
        UserMessage userMessage = new UserMessage();
        CommandParser commandParser = new CommandParser(userMessage, invoker);
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        String[] args = message.split("\\s+");

        Mockito.doNothing().when(invoker)
            .executeCommand(chatId, user, args);

        commandParser.parseCommand(chatId, user, message);

        Mockito.verify(invoker).executeCommand(chatId, user, args);
    }
}
