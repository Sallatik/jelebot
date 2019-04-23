package sallat.jelebot;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.ShippingQuery;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.mockito.Mockito.*;
import static sallat.jelebot.Util.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DispatcherUpdatesListenerTest {

    private UpdatesListener updatesListener;
    private ListenerManager mockedManager;

    @BeforeEach
    void init() {

        mockedManager = mock(ListenerManager.class);
        updatesListener = new DispatcherUpdatesListener(mockedManager);
    }

    @Test
    void dispatches() {

        Update mockedUpdate1 = mock(Update.class);
        Update mockedUpdate2 = mock(Update.class);
        Update mockedUpdate3 = mock(Update.class);

        when(mockedUpdate1.shippingQuery()).thenReturn(mock(ShippingQuery.class));
        when(mockedUpdate2.message()).thenReturn(mock(Message.class));
        when(mockedUpdate3.message()).thenReturn(mock(Message.class));

        updatesListener.process(list(mockedUpdate1, mockedUpdate2, mockedUpdate3));

        verify(mockedManager, times(1)).onShippingQuery(any());
        verify(mockedManager, times(2)).onAnyMessage(any());
    }

}
