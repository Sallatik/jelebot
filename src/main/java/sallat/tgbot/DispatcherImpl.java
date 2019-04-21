package sallat.tgbot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import sallat.parser.PredicateParser;
import sallat.tgbot.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

class DispatcherImpl implements Dispatcher {

    private ListenerFactory listenerFactory;
    private ListenerManager listenerManager;

    @Override
    public void register(Object obj) {

        boolean listenersFound = false;

        for (Method method : obj.getClass().getMethods()) {

            if (method.isAnnotationPresent(MessageListener.class)) {
                listenerManager.addAnyMessageListener(listenerFactory.createMessageListener(obj, method));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(InlineQueryListener.class)) {
                listenerManager.addInlineQueryListener(listenerFactory.createInlineQueryListener(obj, method));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(ChosenInlineResultListener.class)) {
                listenerManager.addChosenInlineResultListener(listenerFactory.createChosenInlineResultListener(obj, method));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(CallbackQueryListener.class)) {
                listenerManager.addCallbackQueryListener(listenerFactory.createCallbackQueryListener(obj, method));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(ShippingQueryListener.class)) {
                listenerManager.addShippingQueryListener(listenerFactory.createShippingQueryListener(obj, method));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(PreCheckoutQueryListener.class)) {
                listenerManager.addPreChekoutQueryListener(listenerFactory.createPreCheckoutQueryListener(obj, method));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(PollListener.class)) {
                listenerManager.addPollListener(listenerFactory.createPollListener(obj, method));
                listenersFound = true;
            }
        }

        if (!listenersFound)
            throw new IllegalArgumentException("No annotated listener methods found in class " + obj.getClass().toString());
    }

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

    DispatcherImpl(ListenerFactory listenerFactory, ListenerManager listenerManager) {

        this.listenerFactory = listenerFactory;
        this.listenerManager = listenerManager;
    }
}
