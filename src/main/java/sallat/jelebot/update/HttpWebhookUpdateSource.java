package sallat.jelebot.update;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetWebhook;
import com.sun.net.httpserver.HttpServer;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetSocketAddress;

/**
 * <code>UpdateSource</code> that uses http webhook to receive updates.<br>
 * As long as telegram only accepts https webhooks, this has to be used together with a https proxy, that will forward all requests to your application in plain http.<br>
 */
public class HttpWebhookUpdateSource implements UpdateSource {

    private String proxyURL;
    private String localPath = "/";
    private InetSocketAddress address;

    /**
     * <ol>
     *     <li>Sets telegram webhook for the supplied proxy url using <code>setWebhook</code> api method.</li>
     *     <li>Creates a http server that listens on the supplied interface and port.</li>
     *     <li>For every incoming http request on the specified local path, decodes updates, calls <code>onUpdate</code> method on the listener, returns <code>200 OK</code> response.</li>
     * </ol>
     * @param listener listener that will transfer updates to Jelebot.
     * @param bot telegram bot who's updates should be received.
     */
    @Override
    public void startGettingUpdates(UpdateListener listener, TelegramBot bot) {

        try {

            // set webhook
            bot.execute(new SetWebhook().url(proxyURL));
            // create server
            HttpServer server = HttpServer.create(address, 10);
            server.createContext(localPath, exchange -> {

                Reader reader = new InputStreamReader(exchange.getRequestBody());
                Update update = BotUtils.parseUpdate(reader);
                listener.onUpdate(update);

                exchange.sendResponseHeaders(200, 0);
                exchange.close();
            });

            server.setExecutor(null);
            server.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets path part of the url to which requests from proxy are going to be redirected.<br>
     * For example, if proxy is configured to send requests to http://localhost:1234/mybot, then the path is "mybot".<br>
     * The default path is "/".
     * @param localPath path
     * @return reference to this instance
     */
    public HttpWebhookUpdateSource setLocalPath(String localPath) {

        this.localPath = localPath;
        return this;
    }

    /**
     * Creates <code>HttpWebhookUpdateSource</code> bound to a given <code>InetSocketAddress</code>
     * @param proxyURL url of the https proxy.
     * @param address your host address.
     */
    public HttpWebhookUpdateSource(String proxyURL, InetSocketAddress address) {

        this.proxyURL = proxyURL;
        this.address = address;
    }
}
