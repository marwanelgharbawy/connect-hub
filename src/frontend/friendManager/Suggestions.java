package frontend.friendManager;

import backend.User;
import friendManager.FriendUtils;
import utils.UIUtils;

import javax.swing.*;
import java.io.IOException;

public class Suggestions extends JFrame {
    private JComboBox<UserComboBoxItem> suggestionsComboBox;
    private JButton sendFriendRequestButton1;
    private JButton deleteButton1;
    private JPanel mainPanel;
    private final User user;

    public Suggestions(User user) {
        this.user = user;
        UIUtils.initializeWindow(this, mainPanel, "Suggestions", 800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        updateComboBox();
        // Setup button listeners for friend request actions
        setupButtonListeners();
    }

    // Set up the button action listeners
    private void setupButtonListeners() {
        // Ignore suggestions
        deleteButton1.addActionListener(_ -> {
            if (suggestionsComboBox.getSelectedItem() != null) {
                UserComboBoxItem userComboBoxItem = (UserComboBoxItem) suggestionsComboBox.getSelectedItem();
                // Remove the suggestion from the suggestion manager
                try {
                    user.getFriendManager().getSuggestionManager().refuseSuggestion(user, userComboBoxItem.getUser());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // After action, update the combo box to reflect the changes
                updateComboBox();
            } else {
                // If no item is selected, show an error message
                JOptionPane.showMessageDialog(null, "No suggestion selected!", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Send friend request to suggestions
        sendFriendRequestButton1.addActionListener(_ -> {
            if (suggestionsComboBox.getSelectedItem() != null) {
                UserComboBoxItem userComboBoxItem = (UserComboBoxItem) suggestionsComboBox.getSelectedItem();
                try {
                    user.getFriendManager().getRequestManager().sendFriendRequest(user, userComboBoxItem.getUser());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // After action, update the combo box to reflect the changes
                updateComboBox();
            } else {
                // If no item is selected, show an error message
                JOptionPane.showMessageDialog(null, "No suggestion selected!", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    // Update combo box with a list of users
    private void updateComboBox() {
        // Clear all items in the combo box
        suggestionsComboBox.removeAllItems();
        // Add each user to the combo box
        for (User suggested : user.getFriendManager().getSuggestionManager().getFriendsOfFriends(user)) {
            suggestionsComboBox.addItem(new UserComboBoxItem(suggested));
        }
    }
}
