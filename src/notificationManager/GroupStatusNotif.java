package notificationManager;

import Group.Group;
import backend.Database;
import backend.User;
import org.json.JSONObject;
import utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GroupStatusNotif implements Notification{
    private Group group;
    private LocalDateTime date;
    private User user;

    public GroupStatusNotif(Group group, User user, LocalDateTime date) {
        this.group = group;
        this.user = user;
        this.date = date;
    }

    public GroupStatusNotif(Group group, User user){
        this.group = group;
        this.user = user;
        this.date = LocalDateTime.now();
    }

    public GroupStatusNotif(Group group, JSONObject json) throws IOException {
        this.group = group;
        this.user = Database.getInstance().getUser(json.getString("user-id"));
        this.date = Utilities.y_M_d_hh_mmToDate(json.getString("date"));
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getNotifMessage() {
        return "your status in "+ group.getName()+ " is changed";
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
        jsonObject.put("user-id", user.getUserId());
        return jsonObject;
    }

    @Override
    public ArrayList<JButton> getNotifBtns() {
        // TODO: setup buttons
        return null;
    }
}
