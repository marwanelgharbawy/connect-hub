package frontend;

import javax.swing.*;
import backend.User;
import utils.UIUtils;

public class CreateGroup extends JFrame {
    private JPanel contentPane;
    private JTextField nameField;
    private JTextField descriptionField;
    private JButton addPhotoButton;
    private JButton createGroupButton;
    private JButton backButton;

    public CreateGroup(User user) {
        UIUtils.initializeWindow(this, contentPane, "Create Group", 400, 400);
        addPhotoButton.addActionListener(e -> {
            addPhoto();
        });
        createGroupButton.addActionListener(e -> {
            createGroup();
        });
        backButton.addActionListener(e -> {
            dispose();
        });
    }

    private void addPhoto() {

    }

    private void createGroup() {
        if (inputIsValid()) {
            String name = nameField.getText();
            String description = descriptionField.getText();

            dispose();
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

        return true;
    }

    public static void main(String[] args) {
        new CreateGroup(null);
    }
}
