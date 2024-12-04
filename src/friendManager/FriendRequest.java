package friendManager;

import user.User;

import java.time.LocalDate;

public class FriendRequest {
    private final User sender; // user.User who sent the friend request
    private final User receiver; // user.User who is receiving the friend request
    private String status; // Status of the friend request : pending, accepted, declined
    private final LocalDate timestamp; // The date on which the friend request was sent

    public FriendRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = "pending";
        this.timestamp = LocalDate.now();
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDate getTimestamp() {
        return timestamp;
    }
    public String getStatus() {
        return status;
    }

    public User getReceiver() {
        return receiver;
    }

    public User getSender() {
        return sender;
    }
}
