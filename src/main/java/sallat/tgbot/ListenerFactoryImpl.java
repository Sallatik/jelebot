package sallat.tgbot;

import com.pengrad.telegrambot.model.*;
import sallat.parser.PredicateParser;
import sallat.tgbot.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ListenerFactoryImpl implements ListenerFactory {

    private PredicateParser<AnyMessage> messagePredicateParser;

    @Override
    public Consumer<AnyMessage> createMessageListener(Object obj, Method method) {

        requireSingleParamOfType(method, Message.class, MessageListener.class);
        MessageListener annotation = method.getAnnotation(MessageListener.class);
        String filter = annotation.filter();
        Predicate<AnyMessage> predicate = messagePredicateParser.parse(filter);

        return anyMessage -> {

            try {

                if (predicate.test(anyMessage))
                    method.invoke(obj, anyMessage.getMessage());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public Consumer<InlineQuery> createInlineQueryListener(Object obj, Method method) {
        requireSingleParamOfType(method, InlineQuery.class, InlineQueryListener.class);
        return createDefaultListener(method, obj);
    }

    @Override
    public Consumer<ChosenInlineResult> createChosenInlineResultListener(Object obj, Method method) {
        requireSingleParamOfType(method, ChosenInlineResult.class, ChosenInlineResultListener.class);
        return createDefaultListener(method, obj);
    }

    @Override
    public Consumer<CallbackQuery> createCallbackQueryListener(Object obj, Method method) {
        requireSingleParamOfType(method, CallbackQuery.class, CallbackQueryListener.class);
        return createDefaultListener(method, obj);
    }

    @Override
    public Consumer<ShippingQuery> createShippingQueryListener(Object obj, Method method) {
        requireSingleParamOfType(method, ShippingQuery.class, ShippingQueryListener.class);
        return createDefaultListener(method, obj);
    }

    @Override
    public Consumer<PreCheckoutQuery> createPreCheckoutQueryListener(Object obj, Method method) {
        requireSingleParamOfType(method, PreCheckoutQuery.class, PreCheckoutQueryListener.class);
        return createDefaultListener(method, obj);
    }

    @Override
    public Consumer<Poll> createPollListener(Object obj, Method method) {
        requireSingleParamOfType(method, Poll.class, PollListener.class);
        return createDefaultListener(method, obj);
    }

    private void requireSingleParamOfType(Method method, Class<?> type, Class<? extends Annotation> annotationClass) throws IllegalArgumentException {

        if (!(method.getParameterCount() == 1 && method.getParameterTypes()[0] == type))
            throw new IllegalArgumentException("Unexpected parameter types for method annotated with "
                    + annotationClass.getSimpleName() + ":\n" + method
                    + "\nExpected parameter type: " + type.getName());
    }

    private <T> Consumer<T> createDefaultListener(Method method, Object object) {

        return target -> {

            try {

                method.invoke(object, target);

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        };
    }

    ListenerFactoryImpl(PredicateParser<AnyMessage> messagePredicateParser) {

        this.messagePredicateParser = messagePredicateParser;
    }

}
