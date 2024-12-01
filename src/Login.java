import javax.swing.*;

public class Login extends JFrame {
    private JPanel contentPane;
    private JPasswordField passwordField1;
    private JTextField textField1;

    public Login() {
        setContentPane(contentPane);
        setTitle("Login");
        setSize(320, 240);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}
