package notificationManager;

import org.json.JSONObject;

import javax.swing.*;
import java.time.LocalDateTime;

public interface Notification {
    String getNotifMessage();
    LocalDateTime getNotifDate();
    ImageIcon getNotifImage();
    JSONObject toJSONObject();
}
