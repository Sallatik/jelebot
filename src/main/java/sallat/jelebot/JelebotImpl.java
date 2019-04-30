package sallat.jelebot;

import com.pengrad.telegrambot.TelegramBot;
import sallat.jelebot.update.UpdateListener;
import sallat.jelebot.update.UpdateSource;

class JelebotImpl implements Jelebot {

    private TelegramBot bot;
    private UpdateListener updateListener;
    private RegisterService registerService;
    private UpdateSource updateSource;

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

        if (updateSource == null)
            throw new IllegalStateException("Update source must not be null");

        updateSource.startGettingUpdates(updateListener, bot);
    }

    @Override
    public void setUpdateSource(UpdateSource updateSource) {
        this.updateSource = updateSource;
    }

    public JelebotImpl(TelegramBot bot, UpdateListener updateListener, RegisterService registerService) {

        this.bot = bot;
        this.updateListener = updateListener;
        this.registerService = registerService;
    }
}
