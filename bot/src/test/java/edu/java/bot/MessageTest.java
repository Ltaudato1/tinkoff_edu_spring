package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.user.UserMessage;
import org.mockito.Mockito;
import org.testng.annotations.Test;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.argThat;

public class MessageTest {
    @Test
    public void testSendMessage() {
        UserMessage userMessage = new UserMessage();
        TelegramBot botMock = Mockito.mock(TelegramBot.class);

        userMessage.setBot(botMock);
        long chatId = 123456789;
        String msg = "Test Message";
        SendMessage sendMessage = new SendMessage(chatId, msg);

        Mockito.when(botMock.execute(Mockito.any(SendMessage.class))).thenReturn(null);

        try {
            userMessage.sendMessage(chatId, msg);

            Mockito.verify(botMock).execute(Mockito.any(SendMessage.class));

        } catch (Exception e) {
            fail();
        }
    }
}
