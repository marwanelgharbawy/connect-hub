package notificationManager;

import org.json.JSONObject;

import java.time.LocalDateTime;

public class NewGroupPostNotif implements Notification {
    @Override
    public String getNotifMessage() {
        return "";
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
