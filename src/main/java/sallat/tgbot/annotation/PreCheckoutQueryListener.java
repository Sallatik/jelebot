package sallat.tgbot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Listener for all incoming <code>PreCheckoutQuery</code> objects.<br>
 * All annotated methods must have one parameter of type {@link com.pengrad.telegrambot.model.PreCheckoutQuery}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PreCheckoutQueryListener {
}
