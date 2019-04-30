package sallat.jelebot;

import com.pengrad.telegrambot.TelegramBot;
import sallat.jelebot.update.UpdateListener;
import sallat.parser.PredicateParser;

class JelebotFactory {

    static Jelebot createJelebot(TelegramBot bot) {

        if (bot == null)
            throw new IllegalArgumentException("Bot can't be null");

        PredicateParser<AnyMessage> predicateParser = new ParserFactory().createMessagePredicateParser();

        ListenerFactory listenerFactory = new ListenerFactoryImpl(predicateParser, bot);
        ListenerManager listenerManager = new ListenerManagerImpl();
        RegisterService registerService = new RegisterServiceImpl(listenerFactory, listenerManager, bot);
        UpdateListener updateListener = new DispatcherUpdateListener(listenerManager);

        return new JelebotImpl(bot, updateListener, registerService);
    }

    static Jelebot createJelebot(String token) {

        if (token == null)
            throw new IllegalArgumentException("Token string can't be null");
        TelegramBot bot = new TelegramBot(token);
        return createJelebot(bot);
    }

}
