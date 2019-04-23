package sallat.jelebot;

import com.pengrad.telegrambot.TelegramBot;
import sallat.jelebot.annotation.InjectTelegramBot;
import sallat.jelebot.annotation.listeners.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class RegisterServiceImpl implements RegisterService {

    private ListenerFactory listenerFactory;
    private ListenerManager listenerManager;
    private TelegramBot bot;

    @Override
    public void register(Object obj) {

        boolean annotationsFound = inject(obj);

        for (Method method : obj.getClass().getDeclaredMethods()) {

            if (method.isAnnotationPresent(MessageListener.class)) {
                listenerManager.addAnyMessageListener(listenerFactory.createMessageListener(obj, method));
                annotationsFound = true;
            }

            if (method.isAnnotationPresent(InlineQueryListener.class)) {
                listenerManager.addInlineQueryListener(listenerFactory.createInlineQueryListener(obj, method));
                annotationsFound = true;
            }

            if (method.isAnnotationPresent(ChosenInlineResultListener.class)) {
                listenerManager.addChosenInlineResultListener(listenerFactory.createChosenInlineResultListener(obj, method));
                annotationsFound = true;
            }

            if (method.isAnnotationPresent(CallbackQueryListener.class)) {
                listenerManager.addCallbackQueryListener(listenerFactory.createCallbackQueryListener(obj, method));
                annotationsFound = true;
            }

            if (method.isAnnotationPresent(ShippingQueryListener.class)) {
                listenerManager.addShippingQueryListener(listenerFactory.createShippingQueryListener(obj, method));
                annotationsFound = true;
            }

            if (method.isAnnotationPresent(PreCheckoutQueryListener.class)) {
                listenerManager.addPreChekoutQueryListener(listenerFactory.createPreCheckoutQueryListener(obj, method));
                annotationsFound = true;
            }

            if (method.isAnnotationPresent(PollListener.class)) {
                listenerManager.addPollListener(listenerFactory.createPollListener(obj, method));
                annotationsFound = true;
            }
        }

        if (!annotationsFound)
            throw new IllegalArgumentException("No annotated methods or methods found in class " + obj.getClass().toString());

    }

    private boolean inject(Object obj) {

        boolean annotationsFound = false;
        try {

            for (Field field : obj.getClass().getDeclaredFields()) {

                if (field.isAnnotationPresent(InjectTelegramBot.class)) {

                    if (!field.getType().equals(TelegramBot.class))
                        throw new IllegalArgumentException("Unexpected type for field annotated with "
                                + "InjectTelegramBot" + ":\n" + field
                                + "\nExpected type: " + TelegramBot.class.getName());

                    field.setAccessible(true);
                    field.set(obj, bot);
                    annotationsFound = true;
                }
            }

            for (Method method : obj.getClass().getDeclaredMethods()) {

                if (method.isAnnotationPresent(InjectTelegramBot.class)) {

                    if (!(method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(TelegramBot.class)))
                        throw new IllegalArgumentException("Unexpected parameter types for method annotated with "
                                + "InjectTelegramBot" + ":\n" + method
                                + "\nExpected parameter type: " + TelegramBot.class.getName());

                    method.setAccessible(true);
                    method.invoke(obj, bot);
                    annotationsFound = true;
                }
            }

        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Error injecting telegram bot", e);
        }

        return annotationsFound;
    }

    public RegisterServiceImpl(ListenerFactory listenerFactory, ListenerManager listenerManager, TelegramBot bot) {
        this.listenerFactory = listenerFactory;
        this.listenerManager = listenerManager;
        this.bot = bot;
    }
}
