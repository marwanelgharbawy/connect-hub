package frontend.friendManager;

import backend.Database;
import backend.User;
import friendManager.FriendUtils;
import org.json.JSONObject;
import utils.UIUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.time.LocalDate;

public class FriendManagerWindow extends JFrame {

    private JButton viewFriendsButton;
    private JButton viewFriendRequestsButton;
    private JButton viewSuggestionsButton;
    private JPanel mainPanel;
    private JButton viewBlockedButton;
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
        viewBlockedButton.addActionListener(_->showBlocked(user));

    }

    private void showFriends(User user) {
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

    private void showFriendRequests(User user) {
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

    private void showSuggestions(User user) {
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
    private void showBlocked(User user) {
        Blocked blocked = new Blocked(user);
        setVisible(false); // Hide Friend Manager window
        blocked.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                setVisible(true); // Show Friend Manager window again
            }
        });
        blocked.setVisible(true);

    }

    public static void main(String[] args) throws IOException {
        Database database = Database.getInstance();
        FriendManagerWindow friendManagerWindow = new FriendManagerWindow(database.getUser("u102"));
        System.out.println("u103 received"+database.getUser("u103").getFriendManager().getRequestManager().getReceivedRequests());
        System.out.println(FriendUtils.havePendingRequest(database.getUser("u103"),database.getUser("u101")));
        System.out.println(FriendUtils.isAlreadyFriends(database.getUser("u103"),database.getUser("u102")));
    }
}
