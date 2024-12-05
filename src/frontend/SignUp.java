package frontend;

import backend.Database;
import utils.Utilities;
import backend.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
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
            LocalDate dateOfBirth = ((Date) dateSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String password = "";

            // Frontend validation

            // Return if any field is empty
            if (username.isEmpty() || email.isEmpty() || Arrays.toString(passwordField.getPassword()).isEmpty() || Arrays.toString(checkPasswordField.getPassword()).isEmpty()){
                JOptionPane.showMessageDialog(this, "All fields are required", "Empty Fields", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Return if username is invalid
            if (!Utilities.validateUsername(username)){
                JOptionPane.showMessageDialog(this, "Invalid username", "Username Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Return if email is invalid
            if (!Utilities.validateEmail(email)){
                JOptionPane.showMessageDialog(this, "Invalid email", "Email Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Return if date of birth is invalid
            if (dateOfBirth.isAfter(LocalDate.now())){
                JOptionPane.showMessageDialog(this, "Invalid date of birth", "Date of Birth Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Return if password don't match
            if (Arrays.toString(passwordField.getPassword()).equals(Arrays.toString(checkPasswordField.getPassword()))){
                password = new String(passwordField.getPassword());
            }
            else {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Password Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Database.getInstance().signUpUser(username, email, password, dateOfBirth);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error creating user", "backend.User Creation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "New user created", "backend.User Created", JOptionPane.INFORMATION_MESSAGE);
            // TODO: Open profile
            // Window pending

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
