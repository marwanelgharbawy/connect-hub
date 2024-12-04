package frontend;

import utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;

public class SignUp extends JFrame{

    public SignUp(){
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
        JButton backButton = new JButton("Back");
        window.add(backButton);
        JButton signUpButton = new JButton("Sign Up");
        window.add(signUpButton);

        signUpButton.addActionListener(_ -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = "";

            // Return if password don't match
            if (Arrays.toString(passwordField.getPassword()).equals(Arrays.toString(checkPasswordField.getPassword()))){
                password = new String(passwordField.getPassword());
            }
            else {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Password Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Return if username or email already exists
            // TODO: Implement this

            Date dateOfBirth = (Date) dateSpinner.getValue();
            if (Utilities.validateEmail(email) && Utilities.validateUsername(username)){
                User user = new User(username, email, password, dateOfBirth);

                // TODO: Add user to database

                JOptionPane.showMessageDialog(this, "New user created", "backend.User Created", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(user.getUserId());

                // TODO: Open new user's profile
            }
            else{
                JOptionPane.showMessageDialog(this, "You entered wrong data", "Wrong Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(_ -> {
            dispose();
            new MainMenu();
        });

        add(window, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SignUp();
    }
}
