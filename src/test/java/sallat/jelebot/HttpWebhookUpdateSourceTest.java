package sallat.jelebot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SetWebhook;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import sallat.jelebot.update.UpdateListener;
import sallat.jelebot.update.HttpWebhookUpdateSource;

import java.net.InetSocketAddress;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HttpWebhookUpdateSourceTest {

    @Test
    void webhookTest() throws Exception {

        String url = "http://localhost:1488/path/";
        int port = 1488;

        HttpWebhookUpdateSource httpWebhookUpdateSource =
                new HttpWebhookUpdateSource(url, new InetSocketAddress(port));

        UpdateListener mockedListener = mock(UpdateListener.class);
        TelegramBot mockedBot = mock(TelegramBot.class);

        httpWebhookUpdateSource.startGettingUpdates(mockedListener, mockedBot);

        verify(mockedBot).execute(argThat(SetWebhook.class::isInstance));

        MediaType json = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, "{message:{message_id:15}}");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        assertEquals(200, response.code());

        verify(mockedListener).onUpdate(argThat(update -> update.message().messageId() == 15));
    }
}
