package sallat.tgbot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
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

    private Collection<Consumer<AnyMessage>> messageListeners = new ArrayList<>();
    private Collection<Consumer<InlineQuery>> inlineQueryListeners = new ArrayList<>();
    private Collection<Consumer<ChosenInlineResult>> chosenInlineResultListeners = new ArrayList<>();
    private Collection<Consumer<CallbackQuery>> callbackQueryListeners = new ArrayList<>();
    private Collection<Consumer<ShippingQuery>> shippingQueryListeners = new ArrayList<>();
    private Collection<Consumer<PreCheckoutQuery>> preCheckoutQueryListeners = new ArrayList<>();
    private Collection<Consumer<Poll>> pollListeners = new ArrayList<>();

    @Override
    public void register(Object obj) {

        boolean listenersFound = false;

        for (Method method : obj.getClass().getMethods()) {

            if (method.isAnnotationPresent(MessageListener.class)) {

                requireSingleParamOfType(method, Message.class, MessageListener.class);
                messageListeners.add(createMessageListener(method, obj, message -> true));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(InlineQueryListener.class)) {

                requireSingleParamOfType(method, InlineQuery.class, InlineQueryListener.class);
                inlineQueryListeners.add(createListener(method, obj, query -> true));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(ChosenInlineResultListener.class)) {

                requireSingleParamOfType(method, ChosenInlineResult.class, ChosenInlineResultListener.class);
                chosenInlineResultListeners.add(createListener(method, obj, result -> true));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(CallbackQueryListener.class)) {

                requireSingleParamOfType(method, CallbackQuery.class, CallbackQueryListener.class);
                callbackQueryListeners.add(createListener(method, obj, query -> true));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(ShippingQueryListener.class)) {

                requireSingleParamOfType(method, ShippingQuery.class, ShippingQueryListener.class);
                shippingQueryListeners.add(createListener(method, obj, query -> true));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(PreCheckoutQueryListener.class)) {

                requireSingleParamOfType(method, PreCheckoutQuery.class, PreCheckoutQueryListener.class);
                preCheckoutQueryListeners.add(createListener(method, obj, query -> true));
                listenersFound = true;
            }

            if (method.isAnnotationPresent(PollListener.class)) {

                requireSingleParamOfType(method, Poll.class, PollListener.class);
                pollListeners.add(createListener(method, obj, poll -> true));
                listenersFound = true;
            }
        }

        if (!listenersFound)
            throw new IllegalArgumentException("No annotated listener methods found in class " + obj.getClass().toString());
    }

    private Consumer<AnyMessage> createMessageListener(Method method, Object object, Predicate<AnyMessage> filter) {

        return target -> {

            try {

                if (filter.test(target))
                    method.invoke(object, target.getMessage());

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        };
    }

    private <T> Consumer<T> createListener(Method method, Object object, Predicate<T> filter) {

        return target -> {

            try {

                if (filter.test(target))
                    method.invoke(object, target);

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        };
    }

    private void requireSingleParamOfType(Method method, Class<?> type, Class<? extends Annotation> annotationClass) throws IllegalArgumentException {

        if (!(method.getParameterCount() == 1 && method.getParameterTypes()[0] == type))
            throw new IllegalArgumentException("Unexpected parameter types for method annotated with "
                    + annotationClass.getSimpleName() + ":\n" + method
                    + "\nExpected parameter type: " + type.getName());
    }

    @Override
    public int process(List<Update> list) {

        list.forEach(update -> {

            if (update.message() != null) {

                Message message = update.message();
                messageListeners.forEach(listener -> listener.accept(new AnyMessage(message, false)));
            } else if (update.editedMessage() != null) {

                Message editedMessage = update.editedMessage();
                messageListeners.forEach(listener -> listener.accept(new AnyMessage(editedMessage, true)));
            } else if (update.channelPost() != null) {

                Message channelPost = update.channelPost();
                messageListeners.forEach(listener -> listener.accept(new AnyMessage(channelPost, false)));
            } else if (update.editedChannelPost() != null) {

                Message editedChannelPost = update.editedChannelPost();
                messageListeners.forEach(listener -> listener.accept(new AnyMessage(editedChannelPost, true)));
            } else if (update.inlineQuery() != null) {

                InlineQuery inlineQuery = update.inlineQuery();
                inlineQueryListeners.forEach(listener -> listener.accept(inlineQuery));
            } else if (update.chosenInlineResult() != null) {

                ChosenInlineResult chosenInlineResult = update.chosenInlineResult();
                chosenInlineResultListeners.forEach(listener -> listener.accept(chosenInlineResult));
            } else if (update.callbackQuery() != null) {

                CallbackQuery callbackQuery = update.callbackQuery();
                callbackQueryListeners.forEach(listener -> listener.accept(callbackQuery));
            } else if (update.shippingQuery() != null) {

                ShippingQuery shippingQuery = update.shippingQuery();
                shippingQueryListeners.forEach(listener -> listener.accept(shippingQuery));
            } else if (update.preCheckoutQuery() != null) {

                PreCheckoutQuery preCheckoutQuery = update.preCheckoutQuery();
                preCheckoutQueryListeners.forEach(listener -> listener.accept(preCheckoutQuery));
            } else if (update.poll() != null) {

                Poll poll = update.poll();
                pollListeners.forEach(listener -> listener.accept(poll));
            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
