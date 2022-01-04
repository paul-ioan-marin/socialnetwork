package socialnetwork.gui.utils;

import socialnetwork.domain.ReplyMessage;
import socialnetwork.domain.User;

public record MessageChat(ReplyMessage message, User user) {

    public ReplyMessage value() {
        return message;
    }

    @Override
    public String toString() {
        return Messages.messageForm(message, user);
    }
}
