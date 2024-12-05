package frontend;

import backend.Database;
import utils.*;

import javax.swing.*;
import java.io.IOException;

public class Login extends JFrame {
    private JPanel contentPane;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JButton backButton;
    private JButton loginButton;

    public Login() {
        UIUtils.initializeWindow(this, contentPane, "Login", 400, 400);
        addEventListeners();
    }

    private void addEventListeners() {
        loginButton.addActionListener(e -> {
            String username = textField1.getText();
            String password = new String(passwordField1.getPassword());

            // Frontend validations

            // Check if fields are empty
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate username
            if (!Utilities.validateUsername(username)) {
                JOptionPane.showMessageDialog(this, "Invalid username", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // TODO: Implement login logic
        });

        backButton.addActionListener(e -> {
            new MainMenu();
            dispose();
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}
