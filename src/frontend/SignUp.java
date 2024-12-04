package frontend;

import backend.Database;
import utils.Utilities;
import backend.User;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.io.IOException;
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
            // Get data from fields
            String username = usernameField.getText();
            String email = emailField.getText();
            Date dateOfBirth = (Date) dateSpinner.getValue();
            String password = "";

            // Frontend validations

            // Return if any field is empty
            if (username.isEmpty() || email.isEmpty() || Arrays.toString(passwordField.getPassword()).isEmpty() || Arrays.toString(checkPasswordField.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Empty Fields", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Return if username is invalid
            if (!Utilities.validateUsername(username)) {
                JOptionPane.showMessageDialog(this, "Username must be between 3 and 20 characters", "Username Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Return if email is invalid
            if (!Utilities.validateEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email", "Email Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Return if date is in the future
            if (((Date) dateSpinner.getValue()).compareTo(new Date()) > 0) {
                JOptionPane.showMessageDialog(this, "Date of birth can't be in the future", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Return if password don't match, and initialize password if they do
            if (Arrays.toString(passwordField.getPassword()).equals(Arrays.toString(checkPasswordField.getPassword()))){
                password = new String(passwordField.getPassword());
            }
            else {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Password Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Backend validations

            // Return if username or email already exists
            // TODO: Implement this

            // Would only reach here if all fields are valid
            User user = new User(username, email, password, dateOfBirth);

            // TODO: Add user to database

            JOptionPane.showMessageDialog(this, "New user created", "User Created", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(user.getUserId());

            // TODO: Open new user's profile
            // Profile window pending
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
