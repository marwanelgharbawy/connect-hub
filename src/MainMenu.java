import Utils.UIUtils;

import javax.swing.*;

public class MainMenu extends JFrame {
    private JPanel contentPane;
    private JButton loginButton;
    private JButton signUpButton;
    private JButton exitButton;

    public MainMenu() {
        UIUtils.initializeWindow(this, contentPane, "Main Menu", 400, 400);

        loginButton.addActionListener(e -> {
            new Login();
            dispose();
        });

        signUpButton.addActionListener(e -> {
            new SignUp();
            dispose();
        });

        exitButton.addActionListener(e -> {
            dispose();
        });
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}