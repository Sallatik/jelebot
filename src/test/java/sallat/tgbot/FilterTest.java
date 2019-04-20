package sallat.tgbot;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import static org.junit.jupiter.api.Assertions.*;

import com.pengrad.telegrambot.model.Voice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sallat.tgbot.annotation.MessageListener;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static sallat.tgbot.Util.*;

public class FilterTest {

    Dispatcher dispatcher;
    FilteredListeners listeners;

    @BeforeEach
    void init() {

        dispatcher = Dispatcher.create();
        listeners = new FilteredListeners();
        dispatcher.register(listeners);
    }

    @Test
    void emptyMessageTest() {

        Message message = mock(Message.class);
        Update update = mock(Update.class);

        when(update.message()).thenReturn(message);

        dispatcher.process(list(update));

        assertEquals(set("noVoiceListener"), listeners.getCalledMethods());
    }

    @Test
    void voiceMessageTest() {

        Voice voice = mock(Voice.class);
        Message message = mock(Message.class);
        Update update = mock(Update.class);

        when(message.voice()).thenReturn(voice);
        when(update.message()).thenReturn(message);

        dispatcher.process(list(update));

        assertEquals(
                set("voiceListener", "photoOrVoiceListener"),
                listeners.getCalledMethods()
        );
    }

    @Test
    void photoMessageTest() {

        PhotoSize photoSize = mock(PhotoSize.class);
        Message message = mock(Message.class);
        Update update = mock(Update.class);

        when(message.photo()).thenReturn(new PhotoSize[] {photoSize});
        when(update.message()).thenReturn(message);

        dispatcher.process(list(update));

        assertEquals(
                set("photoListener", "photoOrVoiceListener", "noVoiceListener"),
                listeners.getCalledMethods()
        );
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
