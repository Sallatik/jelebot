package sallat.jelebot.annotation.listeners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated method will receive incoming <code>CallbackQuery</code> objects.<br>
 * <h2>Parameters</h2>
 * Annotated method is required to declare one parameter of type {@link com.pengrad.telegrambot.model.CallbackQuery}
 * <h2>Return type</h2>
 * A method can have any return type. If a return type is a telegram request, i.e. subclass of {@link com.pengrad.telegrambot.request.BaseRequest},
 * the returned value will be executed.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CallbackQueryListener { }
