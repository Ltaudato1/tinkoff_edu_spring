package edu.java.bot.user;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import java.net.URL;
import java.util.List;
import lombok.Setter;

public class UserMessage {
    public UserMessage() {

    }

    @Setter private TelegramBot bot;

    public void unknownCommand(long chatId) throws Exception {
        sendMessage(chatId, "Неизвестная команда. Используйте /help для получения списка команд");
    }

    public void listCommand(long chatId, List<URL> list) throws Exception {
        StringBuilder message = new StringBuilder();

        for (URL link : list) {
            message.append(link.toString()).append("\n");
        }

        if (message.isEmpty()) {
            sendMessage(chatId, "Список отслеживаемых ссылок пуст.");
        } else {
            sendMessage(chatId, "Список отслеживаемых ссылок:\n" + message.toString());
        }
    }

    public void invalidArgument(long chatId) throws Exception {
        sendMessage(chatId, "Неверный аргумент! Введите /help для помощи");
    }

    public void helpCommand(long chatId) throws Exception {
        sendMessage(chatId, "Доступные команды:\n"
            + "/start - начать работу с ботом\n"
            + "/help - вывести окно с командами\n"
            + "/track <ссылка> - начать отслеживание ссылки\n"
            + "/untrack <ссылка> - прекратить отслеживание ссылки\n"
            + "/list - показать список отслеживаемых ссылок");
    }

    public void successfulAdd(long chatId) throws Exception {
        sendMessage(chatId, "Ссылка успешно добавлена!");
    }

    public void successfulDelete(long chatId) throws Exception {
        sendMessage(chatId, "Ссылка успешно удалена!");
    }

    public void startCommand(long chatId) throws Exception {
        sendMessage(chatId, "Добро пожаловать! Введите /help для помощи или используйте меню");
    }

    public void alreadyHasThisLink(long chatId) throws Exception {
        sendMessage(chatId, "Такая ссылка уже есть!");
    }

    public void noSuchLink(long chatId) throws Exception {
        sendMessage(chatId, "Такой ссылки нет!");
    }

    public void sendMessage(long chatId, String message) throws Exception {
        SendMessage sendMessage = new SendMessage(chatId, message);

        try {
            bot.execute(sendMessage);
        } catch (Exception e) {
            throw new Exception("failed to send message");
        }
    }
}
