package frontend;

import backend.Database;
import utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public class SignUp extends JFrame {
    JButton signUpButton;
    JButton backButton;
    JTextField usernameField;
    JTextField emailField;
    JPasswordField passwordField;
    JPasswordField checkPasswordField;
    JSpinner dateSpinner;
    private MainMenu parent;

    public SignUp(MainMenu parent){
        this.parent = parent;
        initializeUI();
        addListeners();
    }

    private void addListeners() {
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
                String errorMessage = Database.getInstance().signUpUser(username, email, password, dateOfBirth);
                if (errorMessage == null) {
                    JOptionPane.showMessageDialog(this, "New user created", "User Created", JOptionPane.INFORMATION_MESSAGE);

                    // TODO: Open new user's profile
                    // dispose();
                    // Window pending
                } else {
                    JOptionPane.showMessageDialog(this, errorMessage, "User Creation Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error creating user", "backend.User Creation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        });

        backButton.addActionListener(_ -> {
            dispose();
            parent.setVisible(true);
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                parent.setVisible(true);
            }
        });
    }

    private void initializeUI() {
        setTitle("Sign up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        JPanel window = new JPanel(new GridLayout(7, 2, 10, 10));
        window.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        window.add(new JLabel("Username: "));
        usernameField = new JTextField();
        window.add(usernameField);
        window.add(new JLabel("Email: "));
        emailField = new JTextField();
        window.add(emailField);
        window.add(new JLabel("Password: "));
        passwordField = new JPasswordField();
        window.add(passwordField);
        window.add(new JLabel("Confirm Password: "));
        checkPasswordField = new JPasswordField();
        window.add(checkPasswordField);
        window.add(new JLabel("Birthdate: "));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date());
        window.add(dateSpinner);
        backButton = new JButton("Back");
        window.add(backButton);
        signUpButton = new JButton("Sign Up");
        window.add(signUpButton);

        add(window, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
//        new SignUp();
    }
}
