package edu.java.bot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BotRunner implements UpdatesListener {

    private final MyBot myBot;

    public BotRunner(MyBot myBot) {
        this.myBot = myBot;
    }

    public void startBot() {
        myBot.getBot().setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(myBot::processUpdate);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
