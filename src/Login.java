import Utils.UIUtils;

import javax.swing.*;

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
