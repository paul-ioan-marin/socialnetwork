package socialnetwork.domain;

import socialnetwork.domain.constants.Constants.Status;

import java.time.LocalDateTime;

import static socialnetwork.domain.constants.Constants.DATEFORMATTER;
import static socialnetwork.domain.constants.Constants.NULLDATE;

public class FriendshipWithStatus extends Friendship {
    private LocalDateTime date;
    private Status status;

    public FriendshipWithStatus(User user1, User user2) {
        super(user1, user2);
        this.status = Status.PENDING;
        this.date = LocalDateTime.now();
    }

    public FriendshipWithStatus(User user1, User user2, Status status, LocalDateTime date) {
        super(user1, user2);
        this.status = status;
        this.date = date;
    }

    public LocalDateTime getDate() { return date; }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Status status() { return status; }

    public void accept() {
        this.status = Status.ACCEPTED;
        this.date = LocalDateTime.now();
    }

    public void decline() {
        this.status = Status.DECLINED;
    }

    @Override
    public String toString() {
        return super.toString() + " , " + status.getValue() + " , " + date.format(DATEFORMATTER);
    }
}
