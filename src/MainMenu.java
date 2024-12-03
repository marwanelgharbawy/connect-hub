import javax.swing.*;

public class MainMenu extends JFrame {
    private JPanel contentPane;
    private JButton loginButton;
    private JButton signInButton;
    private JButton exitButton;

    public MainMenu() {
        setContentPane(contentPane);
        setTitle("Main Menu");
        setSize(320, 240);
        setVisible(true);

//        loginButton.addActionListener(e -> {
//            new Login();
//            dispose();
//        });
//
//        signInButton.addActionListener(e -> {
//            new SignIn();
//            dispose();
//        });

        exitButton.addActionListener(e -> {
            dispose();
        });
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
