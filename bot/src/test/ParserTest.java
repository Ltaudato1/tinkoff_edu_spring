import edu.java.bot.handlers.CommandHandler;
import edu.java.bot.parsers.CommandParser;
import edu.java.bot.parsers.LinkParser;
import edu.java.bot.user.User;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.mockito.Mockito;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import static edu.java.bot.handlers.UserState.ADD_LINK;
import static edu.java.bot.handlers.UserState.REMOVE_LINK;
import static edu.java.bot.handlers.UserState.UNKNOWN_COMMAND;
import static edu.java.bot.parsers.LinkParser.stringToUrl;

public class ParserTest {

    @Test
    void URLParserTestWithValidLink() throws MalformedURLException {
        String validUrl = "https://example.com";
        URL url = new URL("https://example.com");
        AssertJUnit.assertNotNull(url);
        AssertJUnit.assertEquals(url, LinkParser.stringToUrl(validUrl));
    }

    @Test
    void URLParserTestWithInvalidLink() {
        String invalidUrl = "abacaba";
        AssertJUnit.assertNull(LinkParser.stringToUrl(invalidUrl));
    }

    @Test
    void commandParserTestWithValidMessage() {
        String message = "/track https://abacaba.com";

        CommandHandler commandHandler = Mockito.mock(CommandHandler.class);
        CommandParser commandParser = new CommandParser(commandHandler);
        User user = new User(new ArrayList<>());
        Long chatId = 228L;

        Mockito.doNothing().when(commandHandler)
            .handleCommand(chatId, user, ADD_LINK, stringToUrl("https://abacaba.com"));

        commandParser.parseCommand(chatId, user, message);

        Mockito.verify(commandHandler).handleCommand(chatId, user, ADD_LINK, stringToUrl("https://abacaba.com"));
    }

    @Test
    void commandParserTestWithinvalidCommand() {
        String message = "/message";

        CommandHandler commandHandler = Mockito.mock(CommandHandler.class);
        CommandParser commandParser = new CommandParser(commandHandler);
        User user = new User(new ArrayList<>());
        Long chatId = 228L;

        Mockito.doNothing().when(commandHandler).handleCommand(chatId, user, UNKNOWN_COMMAND, null);

        commandParser.parseCommand(chatId, user, message);

        Mockito.verify(commandHandler).handleCommand(chatId, user, UNKNOWN_COMMAND, null);
    }

    @Test
    void commandParserTestWithinvalidArgument() {
        String message = "/untrack foobar";

        CommandHandler commandHandler = Mockito.mock(CommandHandler.class);
        CommandParser commandParser = new CommandParser(commandHandler);
        User user = new User(new ArrayList<>());
        Long chatId = 228L;

        Mockito.doNothing().when(commandHandler).handleCommand(chatId, user, REMOVE_LINK, null);

        commandParser.parseCommand(chatId, user, message);

        Mockito.verify(commandHandler).handleCommand(chatId, user, REMOVE_LINK, null);
    }
}
