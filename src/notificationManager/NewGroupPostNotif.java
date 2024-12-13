package notificationManager;

import Group.Group;
import content.Post;
import org.json.JSONObject;
import utils.UIUtils;
import utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class NewGroupPostNotif implements Notification {
    private Post post;
    private Group group;

    public NewGroupPostNotif(Group group, Post post) {
        this.post = post;
        this.group = group;
    }

    public NewGroupPostNotif(Group group, JSONObject json){
        this.group = group;
        // TODO: get post from post ID
        // json.get("post-id");
    }

    @Override
    public String getNotifMessage() {
        return "new Post in group: "+group.getName();
    }

    @Override
    public LocalDateTime getNotifDate() {
        return post.getTimestamp();
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
        jsonObject.put("post-id", post.getContentId());
        jsonObject.put("date", Utilities.DataTo_y_M_d_hh_mm(getNotifDate()));
        return null;
    }

    @Override
    public ArrayList<JButton> getNotifBtns() {
        ArrayList<JButton> btns = new ArrayList<>();

        JButton view_group_btn = UIUtils.createNotifButton("View Group");
        view_group_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btns.add(view_group_btn);
        return btns;
    }
}
