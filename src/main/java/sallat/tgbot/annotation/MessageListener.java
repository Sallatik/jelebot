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
 * All annotated methods must have one parameter of type {@link com.pengrad.telegrambot.model.Message}<br>
 * <h2 id="filter">Filters</h2>
 * To filter incoming messages by a particular set of properties, specify a filter string using <code>filter</code> element.
 * Filter string must consist of keywords, i.e <i>filters</i> and boolean operators, delimited by whitespace and parentheses.<br>
 * <h3>Keywords</h3>
 * <table style="width=100%;border:1px solid black;">
 *     <caption style="text-align:left;">Filters</caption>
 *     <tr><th>Filter</th><th>Description</th></tr>
 *     <tr><td><i><b>any</b></i></td><td>Any message or channel post<br>Default value of the <code>filter</code> element.</td></tr>
 *     <tr><td><i><b>edit</b></i></td><td>Edited message or channel post.</td></tr>
 *     <tr><td><i><b>channel</b></i></td><td>A channel post.</td></tr>
 *     <tr><td><i><b>post</b></i></td><td>Same as <i><b>channel</b></i>.</td></tr>
 *     <tr><td><i><b>private</b></i></td><td>A private message.</td></tr>
 *     <tr><td><i><b>group</b></i></td><td>A message from a group.</td></tr>
 *     <tr><td><i><b>supergroup</b></i></td><td>A message from a supergroup.</td></tr>
 *     <tr><td><i><b>forward</b></i></td><td>A forwarded message.</td></tr>
 *     <tr><td><i><b>reply</b></i></td><td>A message, that is a reply to another message.</td></tr>
 *     <tr><td><i><b>text</b></i></td><td>A text message.</td></tr>
 *     <tr><td><i><b>entities</b></i></td><td>A message with either text or caption, containing entities, such as usernames, urls, bot commands, etc.</td></tr>
 *     <tr><td><i><b>audio</b></i></td><td>An audio file.</td></tr>
 *     <tr><td><i><b>file</b></i></td><td>A general file.</td></tr>
 *     <tr><td><i><b>gif</b></i></td><td>An animation.</td></tr>
 *     <tr><td><i><b>game</b></i></td><td>A game.</td></tr>
 *     <tr><td><i><b>photo</b></i></td><td>A photo.</td></tr>
 *     <tr><td><i><b>sticker</b></i></td><td>A sticker.</td></tr>
 *     <tr><td><i><b>video</b></i></td><td>A video file.</td></tr>
 *     <tr><td><i><b>voice</b></i></td><td>A voice message.</td></tr>
 *     <tr><td><i><b>vnote</b></i></td><td>A video note.</td></tr>
 *     <tr><td><i><b>caption</b></i></td><td>A media message with caption.</td></tr>
 *     <tr><td><i><b>contact</b></i></td><td>A shared contact.</td></tr>
 *     <tr><td><i><b>location</b></i></td><td>A shared location.</td></tr>
 *     <tr><td><i><b>venue</b></i></td><td>A venue.</td></tr>
 *     <tr><td><i><b>poll</b></i></td><td>A poll.</td></tr>
 *     <tr><td><i><b>join</b></i></td><td>Message with information about new members in a group or a supergroup.</td></tr>
 *     <tr><td><i><b>left</b></i></td><td>Message with information about a member removed from a group.</td></tr>
 *     <tr><td><i><b>title</b></i></td><td>Message with information about new chat title.</td></tr>
 *     <tr><td><i><b>new_chat_photo</b></i></td><td>A message with information about new chat photo.</td></tr>
 *     <tr><td><i><b>delete_chat_photo</b></i></td><td>A notification that the chat photo was deleted.</td></tr>
 *     <tr><td><i><b>group_created</b></i></td><td>A notification that a group has been created.</td></tr>
 *     <tr><td><i><b>supergroup_created</b></i></td><td>A notification that a supergroup has been created.</td></tr>
 *     <tr><td><i><b>channel_created</b></i></td><td>A notification that a channel has been created.</td></tr>
 *     <tr><td><i><b>migrate</b></i></td><td>The group has been migrated to a supergroup.</td></tr>
 *     <tr><td><i><b>pin</b></i></td><td>A message was pinned.</td></tr>
 *     <tr><td><i><b>invoice</b></i></td><td>An invoice for a payment.</td></tr>
 *     <tr><td><i><b>payment</b></i></td><td>A successful payment.</td></tr>
 *     <tr><td><i><b>website</b></i></td><td>A user logged in to a website.</td></tr>
 *     <tr><td><i><b>passport</b></i></td><td>A telegram passport.</td></tr>
 * </table><br>
 * <table style="width:100%;border:1px solid black;">
 * <caption style="text-align:left;">Boolean operators</caption>
 * <tr><th>Operator</th><th>Description</th></tr>
 * <tr><td><i><b>not</b></i>, <i><b>!</b></i>, <i><b>~</b></i></td><td>NOT operator.</td></tr>
 * <tr><td><i><b>and</b></i>, <i><b>&amp;</b></i>, <i><b>&amp;&amp;</b></i></td><td>AND operator.</td></tr>
 * <tr><td><i><b>or</b></i>, <i><b>|</b></i>, <i><b>||</b></i></td><td>OR operator.</td></tr>
 * <tr><td><i><b>xor</b></i>, <i><b>^</b></i></td><td>XOR operator.</td></tr>
 * </table><br>
 * Operators have the following precedence: <i><b>not</b></i> -&gt; <i><b>and</b></i> -&gt; <i><b>xor</b></i> -&gt; <i><b>or</b></i><br>
 * One might override this by using parentheses.<br>
 * Filter string example: <code>"private and (photo or audio) and caption"</code> will only capture photos and audio files with caption sent via private message.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MessageListener {

    /**
     * A filter string. <a href="#filter">More on filters.</a>
     * @return
     */
    String filter() default "any";
}
