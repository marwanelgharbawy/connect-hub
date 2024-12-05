package frontend;

import backend.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendManagerWindow {

    private JButton viewFriendsButton;
    private JButton viewFriendRequestsButton;
    private JButton viewSuggestionsButton;
    FriendManagerWindow(User user){

        viewFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Friends friends = new Friends(user);
            }
        });
    }
}
