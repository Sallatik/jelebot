package sallat.tgbot;

import com.pengrad.telegrambot.UpdatesListener;

/**
 * Update listener that dispatches different kinds of updates to appropriate annotated listeners.
 */
public interface Dispatcher extends UpdatesListener {

    /**
     * Registers annotated listener methods of this object to receive incoming updates.
     * @param obj instance of a class with annotated methods.
     * @throws IllegalArgumentException if the class of the supplied object does not contain annotated listeners,<br>
     * or any of annotated methods has illegal argument types.
     */
    void register(Object obj);

    /**
     * Create a <code>Dispatcher</code> instance.
     * @return new <code>Dispatcher</code>
     */
    static Dispatcher create() {

        ParserFactory parserFactory = new ParserFactory();
        ListenerFactory listenerFactory = new ListenerFactoryImpl(parserFactory.createMessagePredicateParser());
        ListenerManager listenerManager = new ListenerManagerImpl();

        return new DispatcherImpl(listenerFactory, listenerManager);
    }
}
