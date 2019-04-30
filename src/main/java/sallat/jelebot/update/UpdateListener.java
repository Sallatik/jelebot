package sallat.jelebot.update;

import com.pengrad.telegrambot.model.Update;

/**
 * Represents an object that handles updates from telegram.<br>
 * You don't want to implement this interface, instead, you can use Jelebot-supplied implementation in order to transfer updates to Jelebot, when implementing a custom {@link UpdateSource}
 */
@FunctionalInterface
public interface UpdateListener {

    /**
     * Handles a telegram update.
     * @param update
     */
    void onUpdate(Update update);
}
