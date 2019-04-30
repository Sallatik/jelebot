package sallat.jelebot;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.ShippingQuery;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import sallat.jelebot.update.UpdateListener;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class DispatcherUpdateListenerTest {

    private UpdateListener updatesListener;
    private ListenerManager mockedManager;

    @BeforeEach
    void init() {

        mockedManager = mock(ListenerManager.class);
        updatesListener = new DispatcherUpdateListener(mockedManager);
    }

    @Test
    void dispatches() {

        Update mockedUpdate1 = mock(Update.class);
        Update mockedUpdate2 = mock(Update.class);
        Update mockedUpdate3 = mock(Update.class);

        when(mockedUpdate1.shippingQuery()).thenReturn(mock(ShippingQuery.class));
        when(mockedUpdate2.message()).thenReturn(mock(Message.class));
        when(mockedUpdate3.message()).thenReturn(mock(Message.class));

        updatesListener.onUpdate(mockedUpdate1);
        updatesListener.onUpdate(mockedUpdate2);
        updatesListener.onUpdate(mockedUpdate3);

        verify(mockedManager, times(1)).onShippingQuery(any());
        verify(mockedManager, times(2)).onAnyMessage(any());
    }

}
