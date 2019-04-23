package sallat.jelebot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import sallat.parser.PredicateParser;

class JelebotFactory {

    static Jelebot createJelebot(TelegramBot bot) {

        if (bot == null)
            throw new IllegalArgumentException("Bot can't be null");

        PredicateParser<AnyMessage> predicateParser = new ParserFactory().createMessagePredicateParser();

        ListenerFactory listenerFactory = new ListenerFactoryImpl(predicateParser, bot);
        ListenerManager listenerManager = new ListenerManagerImpl();
        RegisterService registerService = new RegisterServiceImpl(listenerFactory, listenerManager, bot);
        UpdatesListener updatesListener = new DispatcherUpdatesListener(listenerManager);

        return new JelebotImpl(bot, updatesListener, registerService);
    }

    static Jelebot createJelebot(String token) {

        if (token == null)
            throw new IllegalArgumentException("Token string can't be null");
        TelegramBot bot = new TelegramBot(token);
        return createJelebot(bot);
    }

}
