package sallat.tgbot;

import com.pengrad.telegrambot.model.Message;
import sallat.tgbot.annotation.MessageListener;

import java.util.HashSet;
import java.util.Set;

public class FilteredListeners {

    private Set<String> calledMethods = new HashSet<>();

    @MessageListener(filter = "voice")
    public void voiceListener(Message message) {
        calledMethods.add("voiceListener");
    }

    @MessageListener(filter = "photo")
    public void photoListener(Message message) {
        calledMethods.add("photoListener");
    }

    @MessageListener(filter = "photo or voice")
    public void photoOrVoiceListener(Message message) {
        calledMethods.add("photoOrVoiceListener");
    }

    @MessageListener(filter = "not voice")
    public void noVoiceListener(Message message) {
        calledMethods.add("noVoiceListener");
    }

    public Set<String> getCalledMethods() {
        return calledMethods;
    }
}
