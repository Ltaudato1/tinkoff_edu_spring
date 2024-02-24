import edu.java.bot.handlers.CommandHandler;
import edu.java.bot.handlers.UserState;
import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import static edu.java.bot.handlers.UserState.ADD_LINK;
import static edu.java.bot.handlers.UserState.GET_LIST;
import static edu.java.bot.handlers.UserState.HELP;
import static edu.java.bot.handlers.UserState.INVALID_ARGS;
import static edu.java.bot.handlers.UserState.REMOVE_LINK;
import static edu.java.bot.handlers.UserState.START;
import static edu.java.bot.handlers.UserState.UNKNOWN_COMMAND;
import static edu.java.bot.parsers.LinkParser.stringToUrl;

public class HandleTest {
    @ParameterizedTest
    @MethodSource("provideData")
    void testForJustTextCommand(UserState currentState) {
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);

        Mockito.doNothing().when(userMessage).helpCommand(chatId);
        Mockito.doNothing().when(userMessage).startCommand(chatId);
        Mockito.doNothing().when(userMessage).invalidArgument(chatId);
        Mockito.doNothing().when(userMessage).unknownCommand(chatId);

        commandHandler.handleCommand(chatId, user, currentState, null);

        switch (currentState) {
            case HELP -> Mockito.verify(userMessage).helpCommand(chatId);
            case START -> Mockito.verify(userMessage).startCommand(chatId);
            case INVALID_ARGS -> Mockito.verify(userMessage).invalidArgument(chatId);
            case UNKNOWN_COMMAND -> Mockito.verify(userMessage).unknownCommand(chatId);
        }
    }

    @Test
    void trackCommandHandle() {
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        URL argument = stringToUrl("https://example.com");

        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);
        Mockito.doNothing().when(userMessage).successfulAdd(chatId);

        commandHandler.handleCommand(chatId, user, ADD_LINK, argument);
        Assertions.assertFalse(user.trackedList().isEmpty());
        Mockito.verify(userMessage).successfulAdd(chatId);
    }

    @Test
    void untrackCommandHandle() {
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        URL argument = stringToUrl("https://example.com");
        user.addLink(argument);

        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);
        Mockito.doNothing().when(userMessage).successfulDelete(chatId);

        commandHandler.handleCommand(chatId, user, REMOVE_LINK, argument);
        Assertions.assertTrue(user.trackedList().isEmpty());
        Mockito.verify(userMessage).successfulDelete(chatId);
    }

    @Test
    void noSuchLinkTest() {
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        URL argument = stringToUrl("https://example.com");

        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);
        Mockito.doNothing().when(userMessage).noSuchLink(chatId);

        commandHandler.handleCommand(chatId, user, REMOVE_LINK, argument);
        Assertions.assertTrue(user.trackedList().isEmpty());
        Mockito.verify(userMessage).noSuchLink(chatId);
    }

    @Test
    void emptyListCommandTest() {
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);

        Mockito.doNothing().when(userMessage).listCommand(chatId, user.trackedList());

        commandHandler.handleCommand(chatId, user, GET_LIST, null);
        Mockito.verify(userMessage).listCommand(chatId, user.trackedList());
    }

    @Test
    void listCommandTest() {
        User user = new User(new ArrayList<>());
        Long chatId = 228L;
        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);

        String link = "https://example.com";
        user.addLink(stringToUrl(link));

        Mockito.doNothing().when(userMessage).listCommand(chatId, user.trackedList());

        commandHandler.handleCommand(chatId, user, GET_LIST, null);
        Mockito.verify(userMessage).listCommand(chatId, user.trackedList());
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
            Arguments.of(HELP),
            Arguments.of(START),
            Arguments.of(INVALID_ARGS),
            Arguments.of(UNKNOWN_COMMAND)
        );
    }
}
