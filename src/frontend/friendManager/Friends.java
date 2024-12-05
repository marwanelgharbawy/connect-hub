package frontend.friendManager;

import backend.Database;
import backend.User;
import utils.UIUtils;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.IOException;

public class Friends extends JFrame {
    private JComboBox<UserComboBoxItem> friendsComboBox;
    private JPanel mainPanel;
    private JButton blockButton;
    private JButton removeButton;
    private final User user;
    private final Database database;

    Friends(Database database,User user) {
        this.user = user;
        this.database = database;
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
                // Update combo box and database after each deletion
                updateData();
            } else {
                // Display an error message if no friend is selected
                JOptionPane.showMessageDialog(null, "No friend selected!", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        // Block friend
        blockButton.addActionListener(_ -> {
            if (friendsComboBox.getSelectedItem() != null) {
                user.getFriendManager().getBlockManager().blockUser(user, ((UserComboBoxItem) friendsComboBox.getSelectedItem()).getUser());
                // Update combo box and database after each blocking
                updateData();
            } else {
                // Display an error message if no friend is selected
                JOptionPane.showMessageDialog(null, "No friend selected!", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
            }

        });

    }
    void updateData(){
        updateComboBox();
        try {
            database.saveUser(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
