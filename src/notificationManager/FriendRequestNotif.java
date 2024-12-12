package notificationManager;

import backend.User;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
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
    public ImageIcon getNotifImage() {
        ImageIcon originalIcon = sender_user.getProfile().getProfilePhoto().toIcon();
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    @Override
    public JSONObject toJSONObject() {
        return null;
    }
}
