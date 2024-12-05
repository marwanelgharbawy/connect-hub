package frontend;

import backend.User;
import utils.UIUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;

public class FriendManagerWindow extends JFrame {

    private JButton viewFriendsButton;
    private JButton viewFriendRequestsButton;
    private JButton viewSuggestionsButton;
    private JPanel mainPanel;
    private final User user;

    FriendManagerWindow(User user) {
        UIUtils.initializeWindow(this, mainPanel, "Friend Manager", 400, 400);
        this.user = user;
        // Setup button listeners for friend manager menu
        setupButtonListeners();
    }

    // Setup button listeners
    private void setupButtonListeners() {
        viewFriendsButton.addActionListener(_ -> showFriends(user));
        viewFriendRequestsButton.addActionListener(_ -> showFriendRequests(user));
        viewSuggestionsButton.addActionListener(_ -> showSuggestions(user));

    }

    void showFriends(User user) {
        Friends friends = new Friends(user);
        // Hide Friend Manager window
        setVisible(false);
        friends.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                // Show Friend Manager window again
                setVisible(true);
            }
        });
        friends.setVisible(true);

    }

    void showFriendRequests(User user) {
        FriendRequests friendRequests = new FriendRequests(user);
        // Hide Friend Manager window
        setVisible(false);
        friendRequests.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                // Show Friend Manager window again
                setVisible(true);
            }
        });
        friendRequests.setVisible(true);

    }

    void showSuggestions(User user) {
        Suggestions suggestions = new Suggestions(user);
        setVisible(false); // Hide Friend Manager window
        suggestions.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                setVisible(true); // Show Friend Manager window again
            }
        });
        suggestions.setVisible(true);

    }

    public static void main(String[] args) {
        User user = new User();
        FriendManagerWindow friendManagerWindow = new FriendManagerWindow(user);
    }
}
