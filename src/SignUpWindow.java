import user.User;
import user.Utilities;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class SignUpWindow extends JFrame{

    public SignUpWindow(){
        setTitle("Sign up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        JPanel window = new JPanel(new GridLayout(7, 2, 10, 10));
        window.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        window.add(new JLabel("Username: "));
        JTextField usernameField = new JTextField();
        window.add(usernameField);
        window.add(new JLabel("Email: "));
        JTextField emailField = new JTextField();
        window.add(emailField);
        window.add(new JLabel("Password: "));
        JPasswordField passwordField = new JPasswordField();
        window.add(passwordField);
        window.add(new JLabel("Confirm Password: "));
        JPasswordField checkPasswordField = new JPasswordField();
        window.add(checkPasswordField);
        window.add(new JLabel("Birthdate: "));
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date());
        window.add(dateSpinner);
        JButton signUpButton = new JButton("Sign Up");
        window.add(signUpButton);
        JButton signInButton = new JButton("Sign In");
        window.add(signInButton);

        signUpButton.addActionListener(_ -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = "";
            if (passwordField.getPassword() == checkPasswordField.getPassword()){
                password = new String(passwordField.getPassword());
            }
            Date dateOfBirth = (Date) dateSpinner.getValue();
            if (Utilities.validateEmail(email) && Utilities.validateUsername(username)){
                JOptionPane.showMessageDialog(this, "New user created", "user.User Created", JOptionPane.INFORMATION_MESSAGE);
                User user = new User(username, email, password, dateOfBirth);
                System.out.println(user.getUserId());
            }
            else{
                JOptionPane.showMessageDialog(this, "You entered wrong data", "Wrong Input", JOptionPane.ERROR_MESSAGE);
            }
        }

        );
        add(window, BorderLayout.CENTER);
        setVisible(true);
    }

}
