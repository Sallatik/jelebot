package sallat.jelebot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Poll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import sallat.jelebot.annotation.listeners.InlineQueryListener;
import sallat.jelebot.annotation.listeners.MessageListener;
import sallat.jelebot.annotation.listeners.PollListener;
import sallat.jelebot.annotation.InjectTelegramBot;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class RegisterServiceTest {

    private ListenerFactory mockedFactory;
    private ListenerManager mockedManager;
    private TelegramBot mockedBot;

    private RegisterService registerService;

    @BeforeEach
    void init() {

        mockedFactory = mock(ListenerFactory.class);
        mockedManager = mock(ListenerManager.class);
        mockedBot = mock(TelegramBot.class);

        registerService = new RegisterServiceImpl(mockedFactory, mockedManager, mockedBot);
    }

    @Test
    void callsFactoryAndManagerMethods() {

        class Listeners {

            @PollListener
            void pollListener1(Poll poll) { }

            @PollListener
            void pollListener2(Poll poll) { }

            @InlineQueryListener
            void inlineQueryListener(InlineQuery inlineQuery) { }

            @MessageListener
            private void privateMessageListener(Message message) { }
        }

        registerService.register(new Listeners());

        verify(mockedFactory, times(2)).createPollListener(any(), any());
        verify(mockedManager, times(2)).addPollListener(any());

        verify(mockedFactory).createMessageListener(any(), any());
        verify(mockedManager).addAnyMessageListener(any());

        verify(mockedFactory, times(1)).createInlineQueryListener(any(), any());
        verify(mockedManager, times(1)).addInlineQueryListener(any());
    }

    @Test
    void notAnnotatedClassesAreIllegal() {

        assertThrows(IllegalArgumentException.class,
                () -> registerService.register(new Object()));
    }

    @Test
    void injectsTelegramBot() {

        class InjectInto {

            boolean setterCalled = false;

            @InjectTelegramBot
            private void setTelegramBot(TelegramBot bot) {
                setterCalled = true;
            }

            @InjectTelegramBot
            private TelegramBot bot;
        }

        InjectInto object = new InjectInto();
        registerService.register(object);
        assertNotEquals(null, object.bot);
        assertTrue(object.setterCalled);
    }

    @Test
    void illegalParamType() {

        class InjectInto {

            @InjectTelegramBot
            void setTelegramBot(String str) {

            }

            @InjectTelegramBot
            TelegramBot telegramBot;
        }

        assertThrows(IllegalArgumentException.class,
                () -> registerService.register(new InjectInto()));
    }

    @Test
    void annotatedMethodsInSubclass() {

        class Superclass {

            @PollListener
            void listener(){}
        }

        class Subclass extends Superclass {

            @Override
            void listener() {}
        }

        Subclass instance = new Subclass();

        registerService.register(instance);
        verify(mockedFactory).createPollListener(any(), any());
        verify(mockedManager).addPollListener(any());
    }
}

























