package frontend.friendManager;

import backend.Database;
import backend.User;
import friendManager.FriendRequest;
import utils.UIUtils;

import javax.swing.*;
import java.io.IOException;

public class FriendRequests extends JFrame {
    private JComboBox<FriendRequest> requestsComboBox;
    private JPanel mainPanel;
    private JButton declineButton;
    private JButton acceptButton;
    private final User user;
    private final Database database;

    public FriendRequests(Database database,User user) {
        this.user = user;
        this.database = database;
        UIUtils.initializeWindow(this, mainPanel, "Friend Requests", 400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Update the combo box with friend requests
        updateComboBox();
        // Set up button listeners for friend requests actions
        setupButtonListeners();

    }

    // Set up the button action listeners
    private void setupButtonListeners() {
        // Accept request
        acceptButton.addActionListener(_ -> {
            if (requestsComboBox.getSelectedItem() != null) {
                // Call the friend manager and the request manager respectively to accept the request
                user.getFriendManager().getRequestManager().acceptFriendRequest(((RequestComboBoxItem) requestsComboBox.getSelectedItem()).getFriendRequest());
                // Update combo box and database after each addition
                updateData();
            } else {
                // Display error message if no item is selected
                JOptionPane.showMessageDialog(null, "No request selected!", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
            }

        });
        // Decline request
        declineButton.addActionListener(_ -> {
            if (requestsComboBox.getSelectedItem() != null) {
                // Call the friend manager and the request manager respectively to decline the request
                user.getFriendManager().getRequestManager().cancelRequest(((RequestComboBoxItem) requestsComboBox.getSelectedItem()).getFriendRequest());
                // Update combo box and database after each deletion
                updateData();
            } else {
                // Display error message if no item is selected
                JOptionPane.showMessageDialog(null, "No request selected!", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
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
    // Update the friend requests combo box
    void updateComboBox() {
        // Clear all items in the combo box
        requestsComboBox.removeAllItems();
        // Add each request to the combo box
        for (FriendRequest friendRequest : user.getFriendManager().getRequestManager().getReceivedRequests()) {
            requestsComboBox.addItem(friendRequest);
        }

    }
}
