package sallat.tgbot;

import com.pengrad.telegrambot.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import sallat.tgbot.annotation.MessageListener;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DispatcherTest {

    private Dispatcher dispatcher;
    private TestListeners listeners;

    @BeforeEach
    void init() {

        dispatcher = Dispatcher.create();
        listeners = new TestListeners();

        dispatcher.register(listeners);
    }

    @Test
    void illegalArgumentTest() {

        assertThrows(
                IllegalArgumentException.class,
                () -> dispatcher.register(new Object()) // no annotated methods !
        );
    }

    @Test
    void illegalArgumentTypesTest() {

        assertThrows(
                IllegalArgumentException.class,
                () -> dispatcher.register(new Object() {

                    @MessageListener
                    public void onMessage(String text) { } // wrong argument type !
                })
        );
    }
    @Test
    void CallbackQueryListenerTest() {

        Update update = mock(Update.class);
        when(update.callbackQuery()).thenReturn(mock(CallbackQuery.class));

        dispatcher.process(list(update));

        assertEquals(set("CallbackQueryListener"), listeners.getAllCalled());
    }

    @Test
    void ChosenInlineQueryListenerTest() {

        Update update = mock(Update.class);
        when(update.chosenInlineResult()).thenReturn(mock(ChosenInlineResult.class));

        dispatcher.process(list(update));

        assertEquals(set("ChosenInlineResultListener"), listeners.getAllCalled());
    }


    @Test
    void InlineQueryListenerTest() {

        Update update = mock(Update.class);
        when(update.inlineQuery()).thenReturn(mock(InlineQuery.class));

        dispatcher.process(list(update));

        assertEquals(set("InlineQueryListener"), listeners.getAllCalled());
    }


    @Test
    void MessageListenerTest() {

        Update update = mock(Update.class);
        when(update.message()).thenReturn(mock(Message.class));

        dispatcher.process(list(update));

        assertEquals(set("MessageListener"), listeners.getAllCalled());
    }


    @Test
    void PollListenerTest() {

        Update update = mock(Update.class);
        when(update.poll()).thenReturn(mock(Poll.class));

        dispatcher.process(list(update));

        assertEquals(set("PollListener"), listeners.getAllCalled());
    }


    @Test
    void PreCheckoutQueryListenerTest() {

        Update update = mock(Update.class);
        when(update.preCheckoutQuery()).thenReturn(mock(PreCheckoutQuery.class));

        dispatcher.process(list(update));

        assertEquals(set("PreCheckoutQueryListener"), listeners.getAllCalled());
    }

    @Test
    void ShippingQueryListenerTest() {

        Update update = mock(Update.class);
        when(update.shippingQuery()).thenReturn(mock(ShippingQuery.class));

        dispatcher.process(list(update));

        assertEquals(set("ShippingQueryListener"), listeners.getAllCalled());
    }

    @Test
    void ChannelPostListenerTest() {

        Update update = mock(Update.class);
        when(update.channelPost()).thenReturn(mock(Message.class));

        dispatcher.process(list(update));

        // all channel posts should be handled by @MessageListener's
        assertEquals(set("MessageListener"), listeners.getAllCalled());
    }

    @Test
    void EditedMessageListenerTest() {

        Update update = mock(Update.class);
        when(update.editedMessage()).thenReturn(mock(Message.class));

        dispatcher.process(list(update));

        // all edited messages should be handled by @MessageListener's
        assertEquals(set("MessageListener"), listeners.getAllCalled());
    }

    @Test
    void EditedChannelPostListenerTest() {

        Update update = mock(Update.class);
        when(update.editedChannelPost()).thenReturn(mock(Message.class));

        dispatcher.process(list(update));

        // all edited channel posts should be handled by @MessageListener's
        assertEquals(set("MessageListener"), listeners.getAllCalled());
    }

    @SafeVarargs
    final private <T> List<T> list(T... elements) {
        return Stream.of(elements).collect(Collectors.toList());
    }

    @SafeVarargs
    final private <T> Set<T> set(T... elements) {
        return Stream.of(elements).collect(Collectors.toSet());
    }
}
