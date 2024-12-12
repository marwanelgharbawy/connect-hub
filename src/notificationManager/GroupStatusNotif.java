package notificationManager;

import org.json.JSONObject;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GroupStatusNotif implements Notification{
    private String group_id;

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

    @Override
    public ArrayList<JButton> getNotifBtns() {
        return null;
    }
}
