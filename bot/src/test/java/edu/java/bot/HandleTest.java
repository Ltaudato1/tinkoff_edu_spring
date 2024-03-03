package edu.java.bot;

import edu.java.bot.handlers.Invoker;
import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import static edu.java.bot.parsers.LinkParser.stringToUrl;

public class HandleTest {
    @ParameterizedTest @MethodSource("provideData") void testForJustTextCommand(String command) {
        String[] args = {command};
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        UserMessage userMessage = Mockito.mock(UserMessage.class);
        Invoker invoker = new Invoker(userMessage);

        Mockito.doNothing().when(userMessage).helpCommand(chatId);
        Mockito.doNothing().when(userMessage).startCommand(chatId);
        Mockito.doNothing().when(userMessage).unknownCommand(chatId);

        invoker.executeCommand(chatId, user, args);

        switch (command) {
            case "/help" -> Mockito.verify(userMessage).helpCommand(chatId);
            case "/start" -> Mockito.verify(userMessage).startCommand(chatId);
            case "/abacaba" -> Mockito.verify(userMessage).unknownCommand(chatId);
        }
    }

    @Test void trackCommandHandle() {
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        String message = "/track https://example.com";
        String[] args = message.split("\\s+");
        URL urlArgument = stringToUrl("https://example.com");

        UserMessage userMessage = Mockito.mock(UserMessage.class);
        Invoker invoker = new Invoker(userMessage);
        Mockito.doNothing().when(userMessage).successfulAdd(chatId);

        invoker.executeCommand(chatId, user, args);
        Assertions.assertTrue(user.trackedList().contains(urlArgument));
        Mockito.verify(userMessage).successfulAdd(chatId);
    }

    @Test void untrackCommandHandle() {
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        String message = "/untrack https://example.com";
        String[] args = message.split("\\s+");
        URL urlArgument = stringToUrl("https://example.com");
        user.addLink(urlArgument);

        UserMessage userMessage = Mockito.mock(UserMessage.class);
        Invoker invoker = new Invoker(userMessage);
        Mockito.doNothing().when(userMessage).successfulDelete(chatId);

        invoker.executeCommand(chatId, user, args);
        Assertions.assertTrue(user.trackedList().isEmpty());
        Mockito.verify(userMessage).successfulDelete(chatId);
    }

    @Test void noSuchLinkTest() {
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        String message = "/untrack https://example.com";
        String[] args = message.split("\\s+");

        UserMessage userMessage = Mockito.mock(UserMessage.class);
        Invoker invoker = new Invoker(userMessage);
        Mockito.doNothing().when(userMessage).noSuchLink(chatId);

        invoker.executeCommand(chatId, user, args);
        Assertions.assertTrue(user.trackedList().isEmpty());
        Mockito.verify(userMessage).noSuchLink(chatId);
    }

    @Test void emptyListCommandTest() {
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        UserMessage userMessage = Mockito.mock(UserMessage.class);
        Invoker invoker = new Invoker(userMessage);
        String[] args = {"/list"};

        Mockito.doCallRealMethod().when(userMessage).listCommand(chatId, user.trackedList());
        Mockito.doNothing().when(userMessage).sendMessage(chatId, "Список отслеживаемых ссылок пуст.");

        invoker.executeCommand(chatId, user, args);
        Mockito.verify(userMessage).listCommand(chatId, user.trackedList());
        Mockito.verify(userMessage).sendMessage(chatId, "Список отслеживаемых ссылок пуст.");
    }

    @Test void listCommandTest() {
        User user = new User(new ArrayList<>());
        String link = "https://example.com";
        user.addLink(stringToUrl(link));
        Long chatId = 228L;
        UserMessage userMessage = Mockito.mock(UserMessage.class);
        Invoker invoker = new Invoker(userMessage);
        String[] args = {"/list"};

        Mockito.doCallRealMethod().when(userMessage).listCommand(chatId, user.trackedList());
        Mockito.doNothing().when(userMessage).sendMessage(chatId, "Список отслеживаемых ссылок:\n" + link + "\n");

        invoker.executeCommand(chatId, user, args);
        Mockito.verify(userMessage).listCommand(chatId, user.trackedList());
        Mockito.verify(userMessage).sendMessage(chatId, "Список отслеживаемых ссылок:\n" + link + "\n");
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
            Arguments.of("/help"),
            Arguments.of("/start"),
            Arguments.of("/abacaba")
        );
    }
}
