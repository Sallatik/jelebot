package sallat.jelebot.update;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import sallat.jelebot.update.UpdateListener;
import sallat.jelebot.update.UpdateSource;

/**
 * <code>UpdateSource</code> that relies on {@link TelegramBot#setUpdatesListener(UpdatesListener)} to receive updates.<br>
 */
public class LongPollingUpdateSource implements UpdateSource {

    /**
     * Creates an implementation of {@link UpdatesListener} interface, who's <code>process</code> method does the following:
     * <ol>
     *     <li>Calls <code>onUpdate</code> on the supplied listener for each update in the list.</li>
     *     <li>Returns {@link UpdatesListener#CONFIRMED_UPDATES_ALL}</li>
     * </ol>
     * This implementation is then supplied to {@link TelegramBot#setUpdatesListener(UpdatesListener)} method of the supplied bot.
     * @param listener listener that will transfer updates to Jelebot.
     * @param bot telegram bot who's updates should be received.
     */
    @Override
    public void startGettingUpdates(UpdateListener listener, TelegramBot bot) {

        bot.setUpdatesListener(updates -> {
            updates.forEach(listener::onUpdate);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
