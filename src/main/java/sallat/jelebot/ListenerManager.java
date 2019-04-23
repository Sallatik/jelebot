package sallat.jelebot;

import com.pengrad.telegrambot.model.*;

import java.util.function.Consumer;

interface ListenerManager {

    void onAnyMessage(AnyMessage message);

    void onInlineQuery(InlineQuery inlineQuery);

    void onChosenInlineResult(ChosenInlineResult chosenInlineResult);

    void onCallbackQuery(CallbackQuery callbackQuery);

    void onShippingQuery(ShippingQuery shippingQuery);

    void onPreCheckoutQuery(PreCheckoutQuery preCheckoutQuery);

    void onPoll(Poll poll);

    void addAnyMessageListener(Consumer<AnyMessage> listener);

    void addInlineQueryListener(Consumer<InlineQuery> listener);

    void addChosenInlineResultListener(Consumer<ChosenInlineResult> listener);

    void addCallbackQueryListener(Consumer<CallbackQuery> listener);

    void addShippingQueryListener(Consumer<ShippingQuery> listener);

    void addPreChekoutQueryListener(Consumer<PreCheckoutQuery> listener);

    void addPollListener(Consumer<Poll> listener);
}
