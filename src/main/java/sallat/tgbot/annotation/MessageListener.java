package sallat.tgbot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Listener for all incoming <code>Message</code> objects.<br>
 * Those objects will be retrieved from the following fields of the <code>Update</code>:
 * <ul>
 *     <li><code>message</code></li>
 *     <li><code>edited_message</code></li>
 *     <li><code>channel_post</code></li>
 *     <li><code>edited_channel_post</code></li>
 * </ul>
 * All annotated methods must have one parameter of type {@link com.pengrad.telegrambot.model.Message}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MessageListener { }
