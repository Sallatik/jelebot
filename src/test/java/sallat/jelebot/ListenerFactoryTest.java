package sallat.jelebot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sallat.parser.PredicateParser;
import sallat.jelebot.annotation.listeners.*;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ListenerFactoryTest {

    private ListenerFactory listenerFactory;

    @BeforeEach
    void init() {

        PredicateParser<AnyMessage> parser = (PredicateParser<AnyMessage>) mock(PredicateParser.class);
        when(parser.parse(anyString())).thenReturn(s -> true);

        listenerFactory = new ListenerFactoryImpl(parser, null);
    }

    @Test
    void methodsCalled() throws NoSuchMethodException {

        class Listeners {

            String calledMethod;

            @CallbackQueryListener
            private void callbackQueryListener(CallbackQuery callbackQuery) {
                calledMethod = "callbackQueryListener";
            }

            @ChosenInlineResultListener
            public void chosenInlineResultListener(ChosenInlineResult chosenInlineResult) {
                calledMethod = "chosenInlineResultListener";
            }

            @InlineQueryListener
            protected void inlineQueryListener(InlineQuery inlineQuery) {
                calledMethod = "inlineQueryListener";
            }

            @MessageListener
            void messageListener(Message message) {
                calledMethod = "messageListener";
            }

            @PollListener
            void pollListener(Poll poll) {
                calledMethod = "pollListener";
            }

            @PreCheckoutQueryListener
            void preCheckoutQueryListener(PreCheckoutQuery preCheckoutQuery) {
                calledMethod = "preCheckoutQueryListener";
            }

            @ShippingQueryListener
            void shippingQueryListener(ShippingQuery shippingQuery) {
                calledMethod = "shippingQueryListener";
            }
        }

        Method callbackQueryListenerMethod =
                Listeners.class.getDeclaredMethod("callbackQueryListener", CallbackQuery.class);
        Method chosenInlineResultListenerMethod =
                Listeners.class.getDeclaredMethod("chosenInlineResultListener", ChosenInlineResult.class);
        Method inlineQueryListenerMethod =
                Listeners.class.getDeclaredMethod("inlineQueryListener", InlineQuery.class);
        Method messageListenerMethod =
                Listeners.class.getDeclaredMethod("messageListener", Message.class);
        Method pollListenerMethod =
                Listeners.class.getDeclaredMethod("pollListener", Poll.class);
        Method preCheckoutQueryListenerMethod =
                Listeners.class.getDeclaredMethod("preCheckoutQueryListener", PreCheckoutQuery.class);
        Method shippingQueryListenerMethod =
                Listeners.class.getDeclaredMethod("shippingQueryListener", ShippingQuery.class);

        Listeners callback = new Listeners();
        Listeners chosenInlineResult = new Listeners();
        Listeners inline = new Listeners();
        Listeners message = new Listeners();
        Listeners poll = new Listeners();
        Listeners preChedkoutQuery = new Listeners();
        Listeners shippingQuery = new Listeners();

        Consumer<CallbackQuery> callbackQueryListener =
                listenerFactory.createCallbackQueryListener(callback, callbackQueryListenerMethod);
        Consumer<ChosenInlineResult> chosenInlineResultListener =
                listenerFactory.createChosenInlineResultListener(chosenInlineResult, chosenInlineResultListenerMethod);
        Consumer<InlineQuery> inlineQueryListener =
                listenerFactory.createInlineQueryListener(inline, inlineQueryListenerMethod);
        Consumer<AnyMessage> messageListener =
                listenerFactory.createMessageListener(message, messageListenerMethod);
        Consumer<Poll> pollListener =
                listenerFactory.createPollListener(poll, pollListenerMethod);
        Consumer<PreCheckoutQuery> preCheckoutQueryListener =
                listenerFactory.createPreCheckoutQueryListener(preChedkoutQuery, preCheckoutQueryListenerMethod);
        Consumer<ShippingQuery> shippingQueryListener =
                listenerFactory.createShippingQueryListener(shippingQuery, shippingQueryListenerMethod);

        callbackQueryListener.accept(mock(CallbackQuery.class));
        chosenInlineResultListener.accept(mock(ChosenInlineResult.class));
        inlineQueryListener.accept(mock(InlineQuery.class));
        messageListener.accept(mock(AnyMessage.class));
        pollListener.accept(mock(Poll.class));
        preCheckoutQueryListener.accept(mock(PreCheckoutQuery.class));
        shippingQueryListener.accept(mock(ShippingQuery.class));

        assertEquals(callback.calledMethod, "callbackQueryListener");
        assertEquals(chosenInlineResult.calledMethod, "chosenInlineResultListener");
        assertEquals(inline.calledMethod, "inlineQueryListener");
        assertEquals(message.calledMethod, "messageListener");
        assertEquals(poll.calledMethod, "pollListener");
        assertEquals(preChedkoutQuery.calledMethod, "preCheckoutQueryListener");
        assertEquals(shippingQuery.calledMethod, "shippingQueryListener");
    }

    @Test
    void illegalParameterType() throws Exception {

        class ListenerWithIllegalParameterType {

            @InlineQueryListener
            void inlineQueryListener(String wtf) {

            }

            @MessageListener
            void messageListener(Message message, String wtf) {

            }

            @PollListener
            void pollListener() {

            }
        }

        Method inlineQueryListenerMethod =
                ListenerWithIllegalParameterType.class.getDeclaredMethod("inlineQueryListener", String.class);
        Method messageListenerMethod =
                ListenerWithIllegalParameterType.class.getDeclaredMethod("messageListener", Message.class, String.class);
        Method pollListenerMethod =
                ListenerWithIllegalParameterType.class.getDeclaredMethod("pollListener");

        ListenerWithIllegalParameterType listener = new ListenerWithIllegalParameterType();

        assertThrows(IllegalArgumentException.class,
                () -> listenerFactory.createInlineQueryListener(listener, inlineQueryListenerMethod));
        assertThrows(IllegalArgumentException.class,
                () -> listenerFactory.createMessageListener(listener, messageListenerMethod));
        assertThrows(IllegalArgumentException.class,
                ()-> listenerFactory.createPollListener(listener, pollListenerMethod));

    }

    @Test
    void messageFiltersApplied() throws Exception {

        PredicateParser<AnyMessage> mockedParser = (PredicateParser<AnyMessage>) mock(PredicateParser.class);

        when(mockedParser.parse("always false")).thenReturn(m -> false);
        when(mockedParser.parse("always true")).thenReturn(m -> true);

        ListenerFactory listenerFactory = new ListenerFactoryImpl(mockedParser, null);

        class MessageListeners {

            int neverCalledCalledTimes = 0;
            int alwaysCalledCalledTimes = 0;

            @MessageListener(filter = "always false")
            void neverCalled(Message message) {
                neverCalledCalledTimes++;
            }

            @MessageListener(filter = "always true")
            void alwaysCalled(Message message) {
                alwaysCalledCalledTimes++;
            }
        }

        Method neverCalledMethod =
                MessageListeners.class.getDeclaredMethod("neverCalled", Message.class);
        Method alwaysCalledMethod =
                MessageListeners.class.getDeclaredMethod("alwaysCalled", Message.class);

        MessageListeners object = new MessageListeners();

        Consumer<AnyMessage> neverCalledListener =
                listenerFactory.createMessageListener(object, neverCalledMethod);
        Consumer<AnyMessage> alwaysCalledListener =
                listenerFactory.createMessageListener(object, alwaysCalledMethod);

        for (int i = 0; i < 10; i++) {

            neverCalledListener.accept(mock(AnyMessage.class));
            alwaysCalledListener.accept(mock(AnyMessage.class));
        }

        assertEquals(0, object.neverCalledCalledTimes);
        assertEquals(10, object.alwaysCalledCalledTimes);
    }

    @Test
    void responseExecuted() throws Exception {

        TelegramBot mockedBot = mock(TelegramBot.class);

        PredicateParser<AnyMessage> mockedParser = (PredicateParser<AnyMessage>) mock(PredicateParser.class);
        when(mockedParser.parse(anyString())).thenReturn(s -> true);

        ListenerFactory listenerFactory = new ListenerFactoryImpl(mockedParser, mockedBot);

        class ReturnRequestListeners {

            @PollListener
            BaseRequest pollListener(Poll poll) {

                return new SendMessage(666, "Hello!");
            }
        }

        Method pollListenerMethod =
                ReturnRequestListeners.class.getDeclaredMethod("pollListener", Poll.class);

        ReturnRequestListeners object = new ReturnRequestListeners();

        Consumer<Poll> pollListener =
                listenerFactory.createPollListener(object, pollListenerMethod);

        verify(mockedBot, never()).execute(any());
        pollListener.accept(mock(Poll.class));
        verify(mockedBot).execute(any());
    }
}
