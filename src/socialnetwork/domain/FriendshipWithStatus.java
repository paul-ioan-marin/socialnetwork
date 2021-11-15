package socialnetwork.domain;

import socialnetwork.domain.constants.Constants.Status;

import java.time.LocalDate;

public class FriendshipWithStatus extends Friendship {
    private LocalDate date;
    private Status status;

    public FriendshipWithStatus(User user1, User user2) {
        super(user1, user2);
        this.status = Status.PENDING;
    }

    public FriendshipWithStatus(User user1, User user2, Status status) {
        super(user1, user2);
        this.status = status;
    }

    public LocalDate getDate() { return date; }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status status() { return status; }

    public String accept() {
        this.status = Status.ACCEPTED;
        this.date = LocalDate.now();
        return this.status.getValue();
    }

    public String decline() {
        this.status = Status.DECLINED;
        return this.status.getValue();
    }

    @Override
    public String toString() {
        return "Friendship: " + getLeft().getEmail() + " , " + getRight().getEmail() + " , " + status().getValue();
    }
}
