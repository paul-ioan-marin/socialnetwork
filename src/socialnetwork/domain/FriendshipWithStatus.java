package socialnetwork.domain;

import java.time.LocalDate;

public class FriendshipWithStatus extends Friendship {
    private LocalDate date;
    private boolean status;

    public FriendshipWithStatus(User user1, User user2) {
        super(user1, user2);
        this.status = false;
    }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean status() { return status; }

    public void accept() {
        this.status = true;
        this.date = LocalDate.now();
    }

    public void decline() { this.status = false; }
}
