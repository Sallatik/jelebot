package sallat.jelebot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An instance of <code>TelegramBot</code> will be injected in an annotated field or method.<br>
 * Annotated method is required to have one parameter of type <code>TelegramBot</code>.<br>
 * Annotated field is required to be of type <code>TelegramBot</code>.
 * @see com.pengrad.telegrambot.TelegramBot
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface InjectTelegramBot { }
