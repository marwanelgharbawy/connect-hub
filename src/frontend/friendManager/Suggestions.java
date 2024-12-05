package frontend.friendManager;

import backend.Database;
import backend.User;
import utils.UIUtils;

import javax.swing.*;
import java.io.IOException;

public class Suggestions extends JFrame {
    private JComboBox<UserComboBoxItem> suggestionsComboBox;
    private JComboBox<UserComboBoxItem> friendsOfFriendsComboBox;
    private JButton sendFriendRequestButton;
    private JButton deleteButton;
    private JButton sendFriendRequestButton1;
    private JButton deleteButton1;
    private JPanel mainPanel;
    private final User user;
    private final Database database;

    public Suggestions(Database database,User user) {
        this.database = database;
        this.user = user;
        UIUtils.initializeWindow(this, mainPanel, "Suggestions", 800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // Update the combo boxes with user suggestions and friends of friends
        updateComboBoxes();
        // Setup button listeners for friend request actions
        setupButtonListeners();
    }

    // Set up the button action listeners
    private void setupButtonListeners() {
        // Ignore suggestions
        deleteButton1.addActionListener(_ -> implementButtonAction(suggestionsComboBox, true));
        // Send friend request to suggestions
        sendFriendRequestButton1.addActionListener(_ -> implementButtonAction(suggestionsComboBox, false));
        // Ignore friends of friends
        deleteButton.addActionListener(_ -> implementButtonAction(friendsOfFriendsComboBox, true));
        // Send friend request to friends of friends
        sendFriendRequestButton.addActionListener(_ -> implementButtonAction(friendsOfFriendsComboBox, false));
    }

    // Handle the button actions for sending friend requests or deleting suggestions
    private void implementButtonAction(JComboBox<UserComboBoxItem> comboBox, boolean isDeleteAction) {
        // Get the selected item from the combo box
        UserComboBoxItem selectedItem = (UserComboBoxItem) comboBox.getSelectedItem();
        if (selectedItem != null) {
            // If delete action, remove the suggestion from the suggestion manager
            if (isDeleteAction) {
                user.getFriendManager().getSuggestionManager().removeSuggestion(selectedItem.getUser());
            } else {
                // If sending friend request, send the request to the selected user
                user.getFriendManager().getRequestManager().sendFriendRequest(user, selectedItem.getUser());
            }
            // After action, update the combo boxes and database to reflect the changes
            updateData();
        } else {
            // If no item is selected, show an error message
            JOptionPane.showMessageDialog(null, "No suggestion selected!", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateData(){
        updateComboBoxes();
        try {
            database.saveUser(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Update both the suggested and friends of friends combo boxes
    private void updateComboBoxes() {
        // Update the combo box for suggestions
        updateComboBox(suggestionsComboBox, user.getFriendManager().getSuggestionManager().getSuggestions());
        // Update the combo box for friends of friends
        updateComboBox(friendsOfFriendsComboBox, user.getFriendManager().getSuggestionManager().getFriendsOfFriends());
    }

    // Update combo box with a list of users
    private void updateComboBox(JComboBox<UserComboBoxItem> comboBox, Iterable<User> users) {
        // Clear all items in the combo box
        comboBox.removeAllItems();
        // Add each user to the combo box
        for (User suggested : users) {
            comboBox.addItem(new UserComboBoxItem(suggested));
        }
    }
}
