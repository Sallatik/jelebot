package sallat.tgbot;

import com.pengrad.telegrambot.model.Message;

import java.util.function.Predicate;

class AnyMessage {

    private Message message;
    private boolean edited;
    private boolean channelPost;

    public static Predicate<AnyMessage> from(Predicate<Message> predicate) {

        return message -> predicate.test(message.getMessage());
    }

    public Message getMessage() {
        return message;
    }

    public boolean isEdited() {
        return edited;
    }

    public boolean isChannelPost() {
        return channelPost;
    }

    public AnyMessage(Message message, boolean channelPost, boolean edited) {
        this.message = message;
        this.channelPost = channelPost;
        this.edited = edited;
    }
}
