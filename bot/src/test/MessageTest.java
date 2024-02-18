import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.user.UserMessage;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MessageTest {
    @Test
    public void testSendMessage() throws Exception {
        UserMessage userMessage = new UserMessage();
        TelegramBot botMock = Mockito.mock(TelegramBot.class);

        userMessage.setBot(botMock);
        long chatId = 123456789;
        String msg = "Test Message";

        Mockito.when(botMock.execute(Mockito.any(SendMessage.class))).thenReturn(null);

        try {
            userMessage.sendMessage(chatId, msg);

            Mockito.verify(botMock).execute(Mockito.any(SendMessage.class));

        } catch (Exception e) {
            fail();
        }
    }
}
