package sallat.jelebot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;

class JelebotImpl implements Jelebot {

    private TelegramBot bot;
    private UpdatesListener updatesListener;
    private RegisterService registerService;

    @Override
    public Jelebot register(Object obj) {

        registerService.register(obj);
        return this;
    }

    @Override
    public Jelebot register(Object... objects) {

        for (Object obj : objects)
            registerService.register(obj);
        return this;
    }

    @Override
    public void start() {

        bot.setUpdatesListener(updatesListener);
    }

    public JelebotImpl(TelegramBot bot, UpdatesListener updatesListener, RegisterService registerService) {

        this.bot = bot;
        this.updatesListener = updatesListener;
        this.registerService = registerService;
    }
}
