package sallat.jelebot;

import com.pengrad.telegrambot.TelegramBot;
import sallat.jelebot.update.UpdateSource;

import java.util.concurrent.Executor;

class JelebotImpl implements Jelebot {

    private TelegramBot bot;
    private DispatcherUpdateListener updateListener;
    private RegisterService registerService;
    private UpdateSource updateSource;
    private boolean started;

    @Override
    public Jelebot register(Object obj) {

        requireNotStarted();
        registerService.register(obj);
        return this;
    }

    @Override
    public Jelebot register(Object... objects) {

        requireNotStarted();
        for (Object obj : objects)
            registerService.register(obj);
        return this;
    }

    @Override
    public void start() {

        requireNotStarted();
        if (updateSource == null)
            throw new IllegalStateException("Update source must not be null");

        updateSource.startGettingUpdates(updateListener, bot);
    }

    @Override
    public Jelebot setUpdateSource(UpdateSource updateSource) {

        requireNotStarted();
        this.updateSource = updateSource;
        return this;
    }

    @Override
    public Jelebot setExecutor(Executor executor) {

        requireNotStarted();
        updateListener.setExecutor(executor);
        return this;
    }

    private void requireNotStarted() {

        if (started)
            throw new IllegalStateException("Already started");
    }

    public JelebotImpl(TelegramBot bot, DispatcherUpdateListener updateListener, RegisterService registerService) {

        this.bot = bot;
        this.updateListener = updateListener;
        this.registerService = registerService;
    }
}
