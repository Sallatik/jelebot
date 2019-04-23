package sallat.jelebot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;

import java.util.List;

class DispatcherUpdatesListener implements UpdatesListener {

    private ListenerManager listenerManager;

    @Override
    public int process(List<Update> list) {

        list.forEach(update -> {

            if (update.message() != null) {

                Message message = update.message();
                listenerManager.onAnyMessage(new AnyMessage(message, false, false));
            } else if (update.editedMessage() != null) {

                Message editedMessage = update.editedMessage();
                listenerManager.onAnyMessage(new AnyMessage(editedMessage, false, true));
            } else if (update.channelPost() != null) {

                Message channelPost = update.channelPost();
                listenerManager.onAnyMessage(new AnyMessage(channelPost, true, false));
            } else if (update.editedChannelPost() != null) {

                Message editedChannelPost = update.editedChannelPost();
                listenerManager.onAnyMessage(new AnyMessage(editedChannelPost, true, true));
            } else if (update.inlineQuery() != null) {

                InlineQuery inlineQuery = update.inlineQuery();
                listenerManager.onInlineQuery(inlineQuery);
            } else if (update.chosenInlineResult() != null) {

                ChosenInlineResult chosenInlineResult = update.chosenInlineResult();
                listenerManager.onChosenInlineResult(chosenInlineResult);
            } else if (update.callbackQuery() != null) {

                CallbackQuery callbackQuery = update.callbackQuery();
                listenerManager.onCallbackQuery(callbackQuery);
            } else if (update.shippingQuery() != null) {

                ShippingQuery shippingQuery = update.shippingQuery();
                listenerManager.onShippingQuery(shippingQuery);
            } else if (update.preCheckoutQuery() != null) {

                PreCheckoutQuery preCheckoutQuery = update.preCheckoutQuery();
                listenerManager.onPreCheckoutQuery(preCheckoutQuery);
            } else if (update.poll() != null) {

                Poll poll = update.poll();
                listenerManager.onPoll(poll);
            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    DispatcherUpdatesListener(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }
}
