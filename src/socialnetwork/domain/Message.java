package socialnetwork.domain;

import socialnetwork.domain.User;
import socialnetwork.domain.containers.GroupMessage;
import socialnetwork.domain.containers.UserList;
import socialnetwork.domain.primary.Entity;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


public class Message extends Entity<UUID> {

    private User from;
    private GroupMessage to;
    private String message;
    private LocalDateTime date;

    /**
     * The constructor without id
     * @param from The user that sent the message
     * @param to The list of people that received the message
     * @param message The message that was sent
     * @param date When the message was sent
     */
    public Message(User from, GroupMessage to, String message, LocalDateTime date) {
        setId(UUID.randomUUID());
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
    }

    public User getFrom() {
        return from;
    }

    public List<User> getTo() {
        return to.everyoneBut(from);
    }

    public GroupMessage getGroup() { return to; }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = date.format(formatter);
        return new String(from.getFirstName()+" "+from.getLastName() + " : " + message + " at " + timestamp);
    }
}
