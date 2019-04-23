package sallat.jelebot;

import com.pengrad.telegrambot.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

class ListenerManagerImpl implements ListenerManager {

    private Collection<Consumer<AnyMessage>> messageListeners = new ArrayList<>();
    private Collection<Consumer<InlineQuery>> inlineQueryListeners = new ArrayList<>();
    private Collection<Consumer<ChosenInlineResult>> chosenInlineResultListeners = new ArrayList<>();
    private Collection<Consumer<CallbackQuery>> callbackQueryListeners = new ArrayList<>();
    private Collection<Consumer<ShippingQuery>> shippingQueryListeners = new ArrayList<>();
    private Collection<Consumer<PreCheckoutQuery>> preCheckoutQueryListeners = new ArrayList<>();
    private Collection<Consumer<Poll>> pollListeners = new ArrayList<>();

    @Override
    public void onAnyMessage(AnyMessage message) {
        messageListeners.forEach(listener -> listener.accept(message));
    }

    @Override
    public void onInlineQuery(InlineQuery inlineQuery) {
        inlineQueryListeners.forEach(listener -> listener.accept(inlineQuery));
    }

    @Override
    public void onChosenInlineResult(ChosenInlineResult chosenInlineResult) {
        chosenInlineResultListeners.forEach(listener -> listener.accept(chosenInlineResult));
    }

    @Override
    public void onCallbackQuery(CallbackQuery callbackQuery) {
        callbackQueryListeners.forEach(listener -> listener.accept(callbackQuery));
    }

    @Override
    public void onShippingQuery(ShippingQuery shippingQuery) {
        shippingQueryListeners.forEach(listener -> listener.accept(shippingQuery));
    }

    @Override
    public void onPreCheckoutQuery(PreCheckoutQuery preCheckoutQuery) {
        preCheckoutQueryListeners.forEach(listener -> listener.accept(preCheckoutQuery));
    }

    @Override
    public void onPoll(Poll poll) {
        pollListeners.forEach(listener -> listener.accept(poll));
    }

    @Override
    public void addAnyMessageListener(Consumer<AnyMessage> listener) {
        messageListeners.add(listener);
    }

    @Override
    public void addInlineQueryListener(Consumer<InlineQuery> listener) {
        inlineQueryListeners.add(listener);
    }

    @Override
    public void addChosenInlineResultListener(Consumer<ChosenInlineResult> listener) {
        chosenInlineResultListeners.add(listener);
    }

    @Override
    public void addCallbackQueryListener(Consumer<CallbackQuery> listener) {
        callbackQueryListeners.add(listener);
    }

    @Override
    public void addShippingQueryListener(Consumer<ShippingQuery> listener) {
        shippingQueryListeners.add(listener);
    }

    @Override
    public void addPreChekoutQueryListener(Consumer<PreCheckoutQuery> listener) {
        preCheckoutQueryListeners.add(listener);
    }

    @Override
    public void addPollListener(Consumer<Poll> listener) {
        pollListeners.add(listener);
    }
}
