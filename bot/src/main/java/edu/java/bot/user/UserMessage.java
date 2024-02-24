package edu.java.bot.user;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import java.net.URL;
import java.util.List;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2 public class UserMessage {
    public UserMessage() {

    }

    @Setter private TelegramBot bot;

    public void unknownCommand(long chatId) {
        sendMessage(chatId, "Неизвестная команда. Используйте /help для получения списка команд");
    }

    public void listCommand(long chatId, List<URL> list) {
        StringBuilder message = new StringBuilder();

        for (URL link : list) {
            message.append(link.toString()).append("\n");
        }

        if (message.isEmpty()) {
            sendMessage(chatId, "Список отслеживаемых ссылок пуст.");
        } else {
            sendMessage(chatId, "Список отслеживаемых ссылок:\n" + message);
        }
    }

    public void invalidArgument(long chatId) {
        sendMessage(chatId, "Неверный аргумент! Введите /help для помощи");
    }

    public void helpCommand(long chatId) {
        sendMessage(
            chatId,
            "Доступные команды:\n" + "/start - начать работу с ботом\n" + "/help - вывести окно с командами\n" +
                "/track <ссылка> - начать отслеживание ссылки\n" +
                "/untrack <ссылка> - прекратить отслеживание ссылки\n" + "/list - показать список отслеживаемых ссылок"
        );
    }

    public void successfulAdd(long chatId) {
        sendMessage(chatId, "Ссылка успешно добавлена!");
    }

    public void successfulDelete(long chatId) {
        sendMessage(chatId, "Ссылка успешно удалена!");
    }

    public void startCommand(long chatId) {
        sendMessage(chatId, "Добро пожаловать! Введите /help для помощи или используйте меню");
    }

    public void alreadyHasThisLink(long chatId) {
        sendMessage(chatId, "Такая ссылка уже есть!");
    }

    public void noSuchLink(long chatId) {
        sendMessage(chatId, "Такой ссылки нет!");
    }

    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);

        try {
            bot.execute(sendMessage);
        } catch (Exception e) {
            log.error("an error occurred while sending message in chat with ID=" + chatId);
        }
    }
}
