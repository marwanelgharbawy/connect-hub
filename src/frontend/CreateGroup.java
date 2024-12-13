package frontend;

import javax.swing.*;

import backend.Database;
import backend.User;
import utils.UIUtils;
import utils.Utilities;
import Group.Group;

import java.io.IOException;

public class CreateGroup extends JFrame {
    private JPanel contentPane;
    private JTextField nameField;
    private JTextField descriptionField;
    private JButton addPhotoButton;
    private JButton createGroupButton;
    private JButton backButton;
    private String photoPath;
    private User user;

    public CreateGroup(User user) {
        UIUtils.initializeWindow(this, contentPane, "Create Group", 400, 400);
        this.user = user;
        addEventListeners();
    }

    public void addEventListeners() {
        addPhotoButton.addActionListener(e -> {
            photoPath = Utilities.addPicture(this);
        });
        createGroupButton.addActionListener(e -> {
            createGroup();
        });
        backButton.addActionListener(e -> {
            dispose();
        });
    }

    private void createGroup() {
        if (inputIsValid()) {
            String name = nameField.getText();
            String description = descriptionField.getText();

            try {
                Group group = new Group(name, description, photoPath, user);
                user.createGroup(group);
                JOptionPane.showMessageDialog(this, "Group created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Add validations here
    public boolean inputIsValid() {
        String name = nameField.getText();
        String description = descriptionField.getText();

        if (name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validations test passed successfully
        return true;
    }

    public static void main(String[] args) {
        new CreateGroup(null);
    }
}
