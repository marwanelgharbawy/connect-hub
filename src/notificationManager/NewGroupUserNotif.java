package notificationManager;

import Group.Group;
import backend.Database;
import backend.User;
import org.json.JSONObject;
import utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LongSummaryStatistics;

public class NewGroupUserNotif implements Notification{
    private Group group;
    private User user;
    private LocalDateTime date;

    public NewGroupUserNotif(Group group, User user, LocalDateTime date) {
        this.group = group;
        this.user = user;
        this.date = date;
    }

    public NewGroupUserNotif(Group group, User user) {
        this.group = group;
        this.user = user;
        this.date = LocalDateTime.now();
    }

    public NewGroupUserNotif(Group group, JSONObject json) throws IOException {
        this.group = group;
        String user_id = json.getString("user-id");
        LocalDateTime date= Utilities.y_M_d_hh_mmToDate(json.getString("date"));
        this.user = Database.getInstance().getUser(user_id);
        this.date = date;
    }

    @Override
    public String getNotifMessage() {
        return user.getUsername()+" is added to group : "+ group.getName();
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
        jsonObject.put("user-id", user.getUserId());
        jsonObject.put("date", Utilities.DataTo_y_M_d_hh_mm(date));
        return jsonObject;
    }

    @Override
    public ArrayList<JButton> getNotifBtns() {
        // TODO: setup buttons
        return null;
    }
}
