package sallat.jelebot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.BaseRequest;
import sallat.parser.PredicateParser;
import sallat.jelebot.annotation.listeners.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Predicate;

class ListenerFactoryImpl implements ListenerFactory {

    private PredicateParser<AnyMessage> messagePredicateParser;
    private TelegramBot bot;

    @Override
    public Consumer<AnyMessage> createMessageListener(Object obj, Method method) {

        requireSingleParamOfType(method, Message.class, MessageListener.class);
        Consumer<Message> defaultListener = createGenericListener(method, obj);

        String filter = method.getAnnotation(MessageListener.class).filter();

        if (!filter.equals("")) {

            Predicate<AnyMessage> predicate = messagePredicateParser.parse(filter);
            return anyMessage -> {

                if (predicate.test(anyMessage))
                    defaultListener.accept(anyMessage.getMessage());
            };

        } else {

            return anyMessage -> defaultListener.accept(anyMessage.getMessage());
        }
    }

    @Override
    public Consumer<InlineQuery> createInlineQueryListener(Object obj, Method method) {
        requireSingleParamOfType(method, InlineQuery.class, InlineQueryListener.class);
        return createGenericListener(method, obj);
    }

    @Override
    public Consumer<ChosenInlineResult> createChosenInlineResultListener(Object obj, Method method) {
        requireSingleParamOfType(method, ChosenInlineResult.class, ChosenInlineResultListener.class);
        return createGenericListener(method, obj);
    }

    @Override
    public Consumer<CallbackQuery> createCallbackQueryListener(Object obj, Method method) {
        requireSingleParamOfType(method, CallbackQuery.class, CallbackQueryListener.class);
        return createGenericListener(method, obj);
    }

    @Override
    public Consumer<ShippingQuery> createShippingQueryListener(Object obj, Method method) {
        requireSingleParamOfType(method, ShippingQuery.class, ShippingQueryListener.class);
        return createGenericListener(method, obj);
    }

    @Override
    public Consumer<PreCheckoutQuery> createPreCheckoutQueryListener(Object obj, Method method) {
        requireSingleParamOfType(method, PreCheckoutQuery.class, PreCheckoutQueryListener.class);
        return createGenericListener(method, obj);
    }

    @Override
    public Consumer<Poll> createPollListener(Object obj, Method method) {
        requireSingleParamOfType(method, Poll.class, PollListener.class);
        return createGenericListener(method, obj);
    }

    private void requireSingleParamOfType(Method method, Class<?> type, Class<? extends Annotation> annotationClass) throws IllegalArgumentException {

        if (!(method.getParameterCount() == 1 && method.getParameterTypes()[0] == type))
            throw new IllegalArgumentException("Unexpected parameter types for method annotated with "
                    + annotationClass.getSimpleName() + ":\n" + method
                    + "\nExpected parameter type: " + type.getName());
    }

    private <T> Consumer<T> createGenericListener(Method method, Object object) {

        method.setAccessible(true);

        if (bot != null && BaseRequest.class.isAssignableFrom(method.getReturnType())) {

            class Listener<T> implements Consumer<T> {

                private TelegramBot bot;

                @Override
                public void accept(T target) {

                    try {

                        BaseRequest request = (BaseRequest) method.invoke(object, target);
                        bot.execute(request);

                    } catch (IllegalAccessException | InvocationTargetException | RuntimeException e) {
                        e.printStackTrace();
                    }
                }

                public Listener(TelegramBot bot) {
                    this.bot = bot;
                }
            }
            return new Listener<>(bot);

        } else
            
            return target -> {

                try {

                    method.invoke(object, target);

                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            };
    }

    ListenerFactoryImpl(PredicateParser<AnyMessage> messagePredicateParser, TelegramBot bot) {

        this.messagePredicateParser = messagePredicateParser;
        this.bot = bot;
    }

}
