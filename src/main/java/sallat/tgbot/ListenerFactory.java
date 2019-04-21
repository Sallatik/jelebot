package sallat.tgbot;

import com.pengrad.telegrambot.model.*;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public interface ListenerFactory {
    
    Consumer<AnyMessage> createMessageListener(Object obj, Method method);
    
    Consumer<InlineQuery> createInlineQueryListener(Object obj, Method method);
    
    Consumer<ChosenInlineResult> createChosenInlineResultListener(Object obj, Method method);
    
    Consumer<CallbackQuery> createCallbackQueryListener(Object obj, Method method);
    
    Consumer<ShippingQuery> createShippingQueryListener(Object obj, Method method);

    Consumer<PreCheckoutQuery> createPreCheckoutQueryListener(Object obj, Method method);

    Consumer<Poll> createPollListener(Object obj, Method method);
}
