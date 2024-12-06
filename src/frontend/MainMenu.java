package frontend;

import utils.UIUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends JFrame {
    private JPanel contentPane;
    private JButton loginButton;
    private JButton signUpButton;
    private JButton exitButton;

    public MainMenu() {
        UIUtils.initializeWindow(this, contentPane, "Main Menu", 400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(e -> {
            new Login(MainMenu.this);
            dispose();
        });

        signUpButton.addActionListener(e -> {
            new SignUp(MainMenu.this);
            dispose();
        });

        exitButton.addActionListener(e -> {
            dispose();
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}