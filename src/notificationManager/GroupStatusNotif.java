package notificationManager;

import Group.Group;
import org.json.JSONObject;
import utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GroupStatusNotif implements Notification{
    private Group group;
    private LocalDateTime date;

    public GroupStatusNotif(Group group, LocalDateTime date) {
        this.group = group;
        this.date = date;
    }

    public GroupStatusNotif(Group group){
        this.group = group;
        this.date = LocalDateTime.now();
    }

    public GroupStatusNotif(Group group, JSONObject json){
        this.group = group;
        this.date = Utilities.y_M_d_hh_mmToDate(json.getString("date"));
    }

    @Override
    public String getNotifMessage() {
        return group.getName()+"'s status changed";
    }

    @Override
    public LocalDateTime getNotifDate() {
        return date;
    }

    @Override
    public ImageIcon getNotifImage() {
        ImageIcon originalIcon = group.getGroupPhoto().toIcon();
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", Utilities.DataTo_y_M_d_hh_mm(date));
        return jsonObject;
    }

    @Override
    public ArrayList<JButton> getNotifBtns() {
        // TODO: setup buttons
        return null;
    }
}
