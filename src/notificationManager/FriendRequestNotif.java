package notificationManager;

import backend.Database;
import backend.User;
import friendManager.FriendRequest;
import frontend.UserProfile;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FriendRequestNotif implements Notification{
    private FriendRequest friendRequest;

    public FriendRequestNotif(FriendRequest friendRequest) {
        this.friendRequest = friendRequest;
    }

    @Override
    public String getNotifMessage() {
        return friendRequest.getSender().getUsername()+" sent a friend request";
    }

    @Override
    public LocalDateTime getNotifDate() {
        return null;
    }

    @Override
    public ImageIcon getNotifImage() {
        ImageIcon originalIcon = friendRequest.getSender().getProfile().getProfilePhoto().toIcon();
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    @Override
    public JSONObject toJSONObject() {
        return null;
    }

    @Override
    public ArrayList<JButton> getNotifBtns() {
        ArrayList<JButton> btns = new ArrayList<>();

        /* view friend */
//        JButton viewFriend_btn = new JButton("preview");
//        viewFriend_btn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFrame user_profile = new JFrame();
//                try {
//                    Database database = Database.getInstance();
//                    UserProfile user_profile_content = new UserProfile(friendRequest.getReceiver(), friendRequest.getSender());
//                    user_profile.setContentPane(user_profile_content);
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//                user_profile.pack();
//                user_profile.setVisible(true);
//            }
//        });

        /* accept request */
        JButton acceptFriend_btn = new JButton("Accept");
        acceptFriend_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendRequest.getReceiver().getFriendManager().getRequestManager().acceptFriendRequest(friendRequest);
                    for(JButton btn: btns)
                        btn.setVisible(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        /* decline request */
        JButton declineFriend_btn = new JButton("Cancel");
        declineFriend_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    friendRequest.getReceiver().getFriendManager().getRequestManager().cancelRequest(friendRequest);
                    for(JButton btn: btns)
                        btn.setVisible(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btns.add(acceptFriend_btn);
        btns.add(declineFriend_btn);
        return btns;
    }
}
