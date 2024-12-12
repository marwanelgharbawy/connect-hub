package notificationManager;

import backend.User;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class FriendRequestNotif implements Notification{
    private User sender_user;

    public FriendRequestNotif(User sender_user) {
        this.sender_user = sender_user;
    }

    @Override
    public String getNotifMessage() {
        return sender_user.getUsername()+" sent a friend request";
    }

    @Override
    public LocalDateTime getNotifDate() {
        return null;
    }

    @Override
    public JSONObject toJSONObject() {
        return null;
    }
}
