package sallat.jelebot;

import com.pengrad.telegrambot.TelegramBot;

/**
 * Central class in the framework used to assemble your telegram bot modules and run them as a telegram bot.<br>
 * <i>Telegram bot module</i> - is simply an instance of a class containing members annotated with annotations from {@link sallat.jelebot.annotation} and {@link sallat.jelebot.annotation.listeners} packages.
 * <ol type="1">
 *     <li>Obtain an instance using <code>create</code> factory method.</li>
 *     <li>Register the all modules of your bot using <code>register</code> method.</li>
 *     <li>Start getting updates with <code>start</code> method.</li>
 * </ol>
 */
public interface Jelebot {

    /**
     * Register a module to be a part of the bot.
     * @param obj telegram bot module.
     * @return this instance.
     * @throws IllegalArgumentException if no annotations are present or any of them is used incorrectly.
     * @see sallat.jelebot.annotation
     */
    Jelebot register(Object obj);

    /**
     * Register multiple modules to be a part of the bot, as if calling {@link #register(Object)} on each of them.
     * @param objects telegram botot modules.
     * @return this instance.
     * @throws IllegalArgumentException if any of the supplied modules does not contain annotations or any of the annotations is used incorrectly.
     * @see #register(Object)
     */
    Jelebot register(Object... objects);

    /**
     * Start getting updates from telegram.
     */
    void start();

    /**
     * Create new <code>Jelebot</code> instance with an existing <code>TelegramBot</code>.
     * @param bot existing <code>TelegramBot</code>
     * @return new <code>Jelebot</code> instance.
     * @throws IllegalArgumentException if bot is null
     */
    static Jelebot create(TelegramBot bot) {

        return JelebotFactory.createJelebot(bot);
    }

    /**
     * Create new <code>Jelebot</code> instance for your bot token.<br>
     * This is a shorthand for <code>create(new TelegramBot(token))</code>.
     * @param token your telegram bot token.
     * @return new <code>Jelebot</code> instance.
     * @throws IllegalArgumentException if token is null
     */
    static Jelebot create(String token) {

        return JelebotFactory.createJelebot(token);
    }
}
