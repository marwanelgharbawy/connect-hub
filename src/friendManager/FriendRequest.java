package friendManager;

import backend.User;
import org.json.JSONObject;
import utils.Utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FriendRequest {
    private final User sender; // user.User who sent the friend request
    private final User receiver; // user.User who is receiving the friend request
    private FriendRequestEnum status; // Status of the friend request : pending, accepted, declined
    private final LocalDateTime timestamp; // The date on which the friend request was sent

    public FriendRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = FriendRequestEnum.Pending;
        this.timestamp = LocalDateTime.now();
    }
    public FriendRequest(User sender, User receiver, LocalDateTime timestamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = FriendRequestEnum.Pending;
        this.timestamp = timestamp;
    }
    public void setStatus(FriendRequestEnum status) {
        this.status = status;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public FriendRequestEnum getStatus() {
        return status;
    }

    public User getReceiver() {
        return receiver;
    }

    public User getSender() {
        return sender;
    }

    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("sender-id", getSender().getUserId());
        json.put("date", Utilities.DataTo_y_M_d_hh_mm(getTimestamp()));
        return json;
    }
}
