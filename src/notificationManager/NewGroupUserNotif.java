package notificationManager;

import org.json.JSONObject;

import javax.swing.*;
import java.time.LocalDateTime;

public class NewGroupUserNotif implements Notification{
    @Override
    public String getNotifMessage() {
        return "";
    }

    @Override
    public LocalDateTime getNotifDate() {
        return null;
    }

    @Override
    public ImageIcon getNotifImage() {
        return null;
    }

    @Override
    public JSONObject toJSONObject() {
        return null;
    }
}
