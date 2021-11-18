package socialnetwork.domain;

import socialnetwork.domain.containers.UserList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ReplyMessage extends Message{

    private Message reply = null;

    public ReplyMessage(Message reply_message, UUID id, User from, UserList to, String message, LocalDateTime date) {
        super(reply_message.getId(), reply_message.getFrom(), reply_message.getTo(), reply_message.getMessage(), reply_message.getDate());
        reply = new Message(id, from, to, message,date);
    }

    public Message getReply() {
        return reply;
    }

    public void setReply(Message reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String data = this.getDate().format(formatter);
        return new String("The user " + this.getFrom().getFirstName() + " " + this.getFrom().getLastName() +": reply to: " +
                "'" + this.reply.getMessage() + "' with " + this.getMessage() + " timestamp : " + data);
    }
}
