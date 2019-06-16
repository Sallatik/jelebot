package sallat.jelebot.update;

import com.pengrad.telegrambot.TelegramBot;

/**
 * Represents a way for Jelebot to receive updates from telegram.<br>
 * Implementation is responsible to acquire updates from the telegram server for the supplied <code>TelegramBot</code> and transfer them to Jelebot using supplied {@link UpdateListener}.<br>
 * This should start from when the {@link #startGettingUpdates(UpdateListener, TelegramBot)} is called and continue on.
 */
public interface UpdateSource {

    /**
     * Starts receiving updates from telegram and transferring them to the supplied listeners.<br>
     * This method will only be called once, after that, for every incoming update, listener's <code>onUpdate</code> method should be called.
     * @param listener listener that will transfer updates to Jelebot.
     * @param bot telegram bot who's updates should be received.
     */
    void startGettingUpdates(UpdateListener listener, TelegramBot bot);

    /**
     * Stops getting updates from telegram.
     * @param bot
     */
    void stopGettingUpdates(TelegramBot bot);
}
