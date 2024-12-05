package frontend.friendManager;

import backend.User;
import utils.UIUtils;

import javax.swing.*;
import java.io.IOException;

public class Blocked extends JFrame{
    private JComboBox<UserComboBoxItem> blockedComboBox;
    private JPanel mainPanel;
    private JButton unblockButton;
    private final User user;

    public Blocked(User user) {
        UIUtils.initializeWindow(this, mainPanel, "Blocked Users", 400, 400);
        this.user = user;
        updateComboBox();
        unblockButton.addActionListener(_ -> {
            if (blockedComboBox.getSelectedItem() != null) {
                try {
                    user.getFriendManager().getBlockManager().unblockUser(user, ((UserComboBoxItem) blockedComboBox.getSelectedItem()).getUser());
                } catch (IOException i) {
                    throw new RuntimeException(i);
                }
                // Update combo box after each unblock
                updateComboBox();
            } else {
                // Display an error message if no friend is selected
                JOptionPane.showMessageDialog(null, "No user selected!", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
            }

        });
    }
    void updateComboBox() {
        // Clear all items in the combo box
        blockedComboBox.removeAllItems();
        for (User blocked : user.getFriendManager().getBlockManager().getBlockedUsers()) {
            // Add each blocked user to the combo box
            blockedComboBox.addItem(new UserComboBoxItem(blocked));
        }

    }
}
