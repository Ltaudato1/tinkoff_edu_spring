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

@TestInstance(TestInstance.Lifecycle.PER_CLASS) public class HandleTest {
    Stream<Arguments> provideData() {
        return Stream.of(
            Arguments.of(HELP),
            Arguments.of(START),
            Arguments.of(INVALID_ARGS),
            Arguments.of(UNKNOWN_COMMAND)
        );
    }

    @ParameterizedTest
    @MethodSource("provideData")
    void testForJustTextCommand(UserState currentState) throws Exception {
        User user = new User(228, new ArrayList<>());
        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);

        Mockito.doNothing().when(userMessage).helpCommand(user.chatId());
        Mockito.doNothing().when(userMessage).startCommand(user.chatId());
        Mockito.doNothing().when(userMessage).invalidArgument(user.chatId());
        Mockito.doNothing().when(userMessage).unknownCommand(user.chatId());

        commandHandler.handleCommand(user, currentState, null);

        switch (currentState) {
            case HELP -> Mockito.verify(userMessage).helpCommand(user.chatId());
            case START -> Mockito.verify(userMessage).startCommand(user.chatId());
            case INVALID_ARGS -> Mockito.verify(userMessage).invalidArgument(user.chatId());
            case UNKNOWN_COMMAND -> Mockito.verify(userMessage).unknownCommand(user.chatId());
        }
    }

    @Test
    void trackCommandHandle() throws Exception {
        User user = new User(228, new ArrayList<>());
        URL argument = stringToUrl("https://example.com");

        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);
        Mockito.doNothing().when(userMessage).successfulAdd(user.chatId());

        commandHandler.handleCommand(user, ADD_LINK, argument);
        Assertions.assertFalse(user.trackedList().isEmpty());
        Mockito.verify(userMessage).successfulAdd(user.chatId());
    }

    @Test
    void untrackCommandHandle() throws Exception {
        User user = new User(228, new ArrayList<>());
        URL argument = stringToUrl("https://example.com");
        user.addLink(argument);

        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);
        Mockito.doNothing().when(userMessage).successfulDelete(user.chatId());

        commandHandler.handleCommand(user, REMOVE_LINK, argument);
        Assertions.assertTrue(user.trackedList().isEmpty());
        Mockito.verify(userMessage).successfulDelete(user.chatId());
    }

    @Test
    void noSuchLinkTest() throws Exception {
        User user = new User(228, new ArrayList<>());
        URL argument = stringToUrl("https://example.com");

        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);
        Mockito.doNothing().when(userMessage).noSuchLink(user.chatId());

        commandHandler.handleCommand(user, REMOVE_LINK, argument);
        Assertions.assertTrue(user.trackedList().isEmpty());
        Mockito.verify(userMessage).noSuchLink(user.chatId());
    }

    @Test
    void emptyListCommandTest() throws Exception {
        User user = new User(228, new ArrayList<>());
        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);

        Mockito.doNothing().when(userMessage).listCommand(user.chatId(), user.trackedList());

        commandHandler.handleCommand(user, GET_LIST, null);
        Mockito.verify(userMessage).listCommand(user.chatId(), user.trackedList());
    }

    @Test
    void listCommandTest() throws Exception {
        User user = new User(228, new ArrayList<>());
        UserMessage userMessage = Mockito.mock(UserMessage.class);
        CommandHandler commandHandler = new CommandHandler(userMessage);

        String link = "https://example.com";
        user.addLink(stringToUrl(link));

        Mockito.doNothing().when(userMessage).listCommand(user.chatId(), user.trackedList());

        commandHandler.handleCommand(user, GET_LIST, null);
        Mockito.verify(userMessage).listCommand(user.chatId(), user.trackedList());
    }
}
