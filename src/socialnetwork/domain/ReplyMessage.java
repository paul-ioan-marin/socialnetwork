package socialnetwork.domain;

import socialnetwork.domain.containers.UserList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ReplyMessage extends Message{

    private Message reply ;

    public ReplyMessage(Message reply_message, User from, UserList to, String message, LocalDateTime date) {
        super(from, to, message, date);
        this.reply=reply_message;
    }

    public ReplyMessage(User from, UserList to, String message, LocalDateTime date) {
        super(from, to, message, date);
        this.reply=null;
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
