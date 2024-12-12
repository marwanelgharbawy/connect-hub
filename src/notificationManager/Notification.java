package notificationManager;

import org.json.JSONObject;

import java.time.LocalDateTime;

public interface Notification {
    String getNotifMessage();
    LocalDateTime getNotifDate();
    JSONObject toJSONObject();
}
