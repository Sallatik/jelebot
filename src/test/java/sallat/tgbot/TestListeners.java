package sallat.tgbot;

import com.pengrad.telegrambot.model.*;
import sallat.tgbot.annotation.*;

import java.util.HashSet;
import java.util.Set;

public class TestListeners {

    private String lastCalled;
    private Set<String> allCalled = new HashSet<>();

    @CallbackQueryListener
    public void onCallbackQuery(CallbackQuery callbackQuery) {
        lastCalled = "CallbackQueryListener";
        allCalled.add(lastCalled);
    }

    @ChosenInlineResultListener
    public void onChosenInlineResult(ChosenInlineResult chosenInlineResult) {
        lastCalled = "ChosenInlineResultListener";
        allCalled.add(lastCalled);
    }

    @InlineQueryListener
    public void onInlineQuery(InlineQuery query) {
        lastCalled = "InlineQueryListener";
        allCalled.add(lastCalled);
    }

    @MessageListener
    public void onMessage(Message message) {
        lastCalled = "MessageListener";
        allCalled.add(lastCalled);
    }

    @PollListener
    public void onPoll(Poll poll) {
        lastCalled = "PollListener";
        allCalled.add(lastCalled);
    }

    @PreCheckoutQueryListener
    public void onPreCheckout(PreCheckoutQuery query) {
        lastCalled = "PreCheckoutQueryListener";
        allCalled.add(lastCalled);
    }

    @ShippingQueryListener
    public void onShipping(ShippingQuery shippingQuery) {
        lastCalled = "ShippingQueryListener";
        allCalled.add(lastCalled);
    }

    @MessageListener(filter = "private")
    public void onPrivateMessage(Message message) {
        lastCalled = "PrivateMessageListener";
        allCalled.add(lastCalled);
    }

    public String getLastCalled() {
        return lastCalled;
    }

    public Set<String> getAllCalled() {
        return allCalled;
    }
}
