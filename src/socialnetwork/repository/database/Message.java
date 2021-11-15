package socialnetwork.repository.database;

import socialnetwork.domain.User;
import socialnetwork.domain.containers.UserList;
import socialnetwork.domain.primary.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Message extends Entity<UUID> {

    private User from;
    private UserList to;
    private String message;
    private LocalDateTime date;

    /**
     * The constructor without id
     * @param from The user that sent the message
     * @param to The list of people that received the message
     * @param message The message that was sent
     * @param date When the message was sent
     */
    public Message(User from, UserList to, String message, LocalDateTime date) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
    }

    /**
     * Constructor with id
     * @param id the id of the message
     * @param from The user that sent the message
     * @param to The list of people that received the message
     * @param message The message that was sent
     * @param date When the message was sent
     */
    public Message(UUID id,User from, UserList to, String message, LocalDateTime date) {
        this.setId(id);
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
    }

    @Override
    public String getFromColumn(int column) {
        return null;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public UserList getTo() {
        return to;
    }

    public void setTo(UserList to) {
        this.to = to;
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
