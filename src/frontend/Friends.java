package frontend;

import backend.User;
import utils.UIUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Friends extends JFrame {
    private JComboBox<UserComboBoxItem> friendsComboBox;
    private JPanel mainPanel;
    private JButton blockButton;
    private JButton removeButton;
    private final User user;
    Friends(User user){
        this.user = user;
        UIUtils.initializeWindow(this, mainPanel, "Friends", 400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get friend from the combo box
                UserComboBoxItem userComboBoxItem = (UserComboBoxItem) friendsComboBox.getSelectedItem();
                // Remove friend
                if (friendsComboBox.getSelectedItem()!=null){
                    user.getFriendManager().removeFriend(user,((UserComboBoxItem) friendsComboBox.getSelectedItem()).getUser());
                }
                else {
                    JOptionPane.showMessageDialog(null,"No friend selected!","Empty Field Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get friend from the combo box
                UserComboBoxItem userComboBoxItem = (UserComboBoxItem) friendsComboBox.getSelectedItem();
                // Block friend
                if (friendsComboBox.getSelectedItem()!=null){
                    user.getFriendManager().getBlockManager().blockUser(user,((UserComboBoxItem) friendsComboBox.getSelectedItem()).getUser());
                }
                else {
                    JOptionPane.showMessageDialog(null,"No friend selected!","Empty Field Error",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
    void populateComboBox(){
        for(User friend : user.getFriendManager().getFriends()){
            friendsComboBox.addItem(new UserComboBoxItem(user));
        }

    }
}
