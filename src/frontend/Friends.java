package frontend;

import backend.User;
import utils.UIUtils;

import javax.swing.*;

public class Friends extends JFrame {
    private JComboBox<UserComboBoxItem> friendsComboBox;
    private JPanel mainPanel;
    private JButton blockButton;
    private JButton removeButton;
    private final User user;

    Friends(User user) {
        this.user = user;
        UIUtils.initializeWindow(this, mainPanel, "Friends", 400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Update combo box with user's friends
        updateComboBox();
        // Setup button listeners for friend actions
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        // Remove friend
        removeButton.addActionListener(_ -> {
            if (friendsComboBox.getSelectedItem() != null) {
                user.getFriendManager().removeFriend(user, ((UserComboBoxItem) friendsComboBox.getSelectedItem()).getUser());
                // Update combo box after each deletion
                updateComboBox();
            } else {
                // Display an error message if no friend is selected
                JOptionPane.showMessageDialog(null, "No friend selected!", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Block friend
        blockButton.addActionListener(_ -> {
            if (friendsComboBox.getSelectedItem() != null) {
                user.getFriendManager().getBlockManager().blockUser(user, ((UserComboBoxItem) friendsComboBox.getSelectedItem()).getUser());
                // Update combo box after each blocking
                updateComboBox();
            } else {
                // Display an error ,essage if no friend is selected
                JOptionPane.showMessageDialog(null, "No friend selected!", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
            }

        });

    }

    void updateComboBox() {
        // Clear all items in the combo box
        friendsComboBox.removeAllItems();
        for (User friend : user.getFriendManager().getFriends()) {
            // Add each friend to the combo box
            friendsComboBox.addItem(new UserComboBoxItem(friend));
        }

    }
}
