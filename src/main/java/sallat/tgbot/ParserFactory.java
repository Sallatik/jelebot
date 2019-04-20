package sallat.tgbot;

import com.pengrad.telegrambot.model.Chat;
import sallat.parser.CasePolicy;
import sallat.parser.Operators;
import sallat.parser.PredicateParser;
import sallat.parser.SimplePredicateParser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static sallat.parser.Operators.*;
import static sallat.tgbot.AnyMessage.from;

class ParserFactory {

    PredicateParser<AnyMessage> createMessagePredicateParser() {

        return SimplePredicateParser.<AnyMessage>builder()
                .setCasePolicy(CasePolicy.TO_LOWER_CASE)
                .setOperatorMap(createOperatorsMap())
                .setPredicateMap(createPredicateMap())
                .build();
    }

    private Map<String, Operators> createOperatorsMap() {

        Map<String, Operators> operatorsMap = new HashMap<>();

        operatorsMap.put("not", NOT);
        operatorsMap.put("and", AND);
        operatorsMap.put("or", OR);
        operatorsMap.put("xor", XOR);

        operatorsMap.put("~", NOT);
        operatorsMap.put("&", AND);
        operatorsMap.put("|", OR);
        operatorsMap.put("^", XOR);

        operatorsMap.put("&&", AND);
        operatorsMap.put("||", OR);
        operatorsMap.put("!", NOT);

        return operatorsMap;
    }

    private Map<String, Predicate<AnyMessage>> createPredicateMap() {

        Map<String, Predicate<AnyMessage>> predicateMap = new HashMap<>();

        predicateMap.put("any", message -> true);

        predicateMap.put("edit", AnyMessage::isEdited);

        predicateMap.put("channel", AnyMessage::isChannelPost);
        predicateMap.put("post", AnyMessage::isChannelPost);

        predicateMap.put("private", from(message ->
            message.chat().type() == Chat.Type.Private
        ));

        predicateMap.put("group", from(message ->
            message.chat().type() == Chat.Type.group
        ));

        predicateMap.put("supergroup", from(message ->
            message.chat().type() == Chat.Type.supergroup
        ));

        predicateMap.put("forward", from(message ->
                message.forwardFrom() != null ||
                        message.forwardSenderName() != null ||
                        message.forwardFromChat() != null ||
                        message.forwardDate() != null
        ));

        predicateMap.put("reply", from(message ->
                message.replyToMessage() != null
        ));

        predicateMap.put("text", from(message ->
                message.text() != null
        ));

        predicateMap.put("entities", from(message ->
                message.entities() != null ||
                        message.captionEntities() != null
        ));

        predicateMap.put("audio", from(message ->
                message.audio() != null
        ));

        predicateMap.put("file", from(message ->
                message.document() != null
        ));

        predicateMap.put("gif", from(message ->
                message.animation() != null
        ));

        predicateMap.put("game", from(message ->
                message.game() != null
        ));

        predicateMap.put("photo", from(message ->
                message.photo() != null
        ));

        predicateMap.put("sticker", from(message ->
                message.sticker() != null
        ));

        predicateMap.put("video", from(message ->
                message.video() != null
        ));

        predicateMap.put("voice", from(message ->
                message.voice() != null
        ));

        predicateMap.put("vnote", from(message ->
                message.videoNote() != null
        ));

        predicateMap.put("caption", from(message ->
                message.caption() != null
        ));

        predicateMap.put("contact", from(message ->
                message.contact() != null
        ));

        predicateMap.put("location", from(message ->
                message.location() != null
        ));

        predicateMap.put("venue", from(message ->
                message.venue() != null
        ));

        predicateMap.put("poll", from(message ->
                message.poll() != null
        ));

        predicateMap.put("join", from(message ->
                message.newChatMembers() != null
        ));

        predicateMap.put("left", from(message ->
                message.leftChatMember() != null
        ));

        predicateMap.put("title", from(message ->
                message.newChatTitle() != null
        ));

        predicateMap.put("new_chat_photo", from(message ->
                message.newChatPhoto() != null
        ));

        predicateMap.put("delete_chat_photo", from(message ->
                message.deleteChatPhoto() != null
        ));

        predicateMap.put("group_created", from(message ->
                message.groupChatCreated() != null
        ));

        predicateMap.put("supergroup_created", from(message ->
                message.supergroupChatCreated() != null
        ));

        predicateMap.put("channel_created", from(message ->
                message.channelChatCreated() != null
        ));

        predicateMap.put("migrate", from(message ->
                message.migrateToChatId() != null ||
                        message.migrateFromChatId() != null
        ));

        predicateMap.put("pin", from(message ->
                message.pinnedMessage() != null
        ));

        predicateMap.put("invoice", from(message ->
                message.invoice() != null
        ));

        predicateMap.put("payment", from(message ->
                message.successfulPayment() != null
        ));

        predicateMap.put("website", from(message ->
                message.connectedWebsite() != null
        ));

        predicateMap.put("passport", from(message ->
                message.passportData() != null
        ));

        return predicateMap;
    }
}
