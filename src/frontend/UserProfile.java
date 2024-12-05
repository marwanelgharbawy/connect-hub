package frontend;

import backend.Profile;
import backend.User;
import content.ContentFields;
import content.Post;
import frontend.newsFeed.PostCard;
import utils.Utilities;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public class UserProfile extends JPanel {

    JPanel mainPanel;
    JPanel coverPhotoPanel;
    JLabel coverPhotoLabel;
    JPanel profilePhotoPanel;
    JLabel profilePhotoLabel;
    JLabel usernameLabel;
    JPanel mainContentPanel;
    JPanel bioPanel;
    JLabel bioLabel;
    JButton settingsButton;
    JPanel postsPanel;
    JScrollPane scrollPane;

    public UserProfile(User user) throws IOException {
        setSize(700, 800);
        setLayout(new BorderLayout());

        CurrentUserBuilder builder = new CurrentUserBuilder(user);
        JPanel mainPanel = builder.build();

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);

    }

    public UserProfile(User currentUser, User desiredUser) throws IOException {
        setSize(700, 800);
        setLayout(new BorderLayout());

        UserProfileBuilder builder = new UserProfileBuilder(currentUser, desiredUser);
        JPanel mainPanel = builder.build();

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void showProfileSettings(User user) {
        JPanel settingsPanel = new JPanel(new GridLayout(7, 1));
        JButton usernameButton = new JButton("Change username");
        JButton emailButton = new JButton("Change email");
        JButton passwordButton = new JButton("Change password");
        JButton dobButton = new JButton("Change date of birth");
        JButton profilePictureButton = new JButton("Change profile picture");
        JButton coverPhotoButton = new JButton("Change cover photo");
        JButton bioButton = new JButton("Change bio");
        usernameButton.addActionListener(_ -> changeUsername(user));
        settingsPanel.add(emailButton);
        emailButton.addActionListener(_ -> changeEmail(user));
        settingsPanel.add(passwordButton);
        passwordButton.addActionListener(_ -> changePassword(user));
        settingsPanel.add(dobButton);
        dobButton.addActionListener(_ -> changeDob(user));
        settingsPanel.add(profilePictureButton);
        profilePictureButton.addActionListener(_ -> {
            try {
                changeProfilePicture(user.getProfile());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Can not change profile picture", "Failed upload", JOptionPane.ERROR_MESSAGE);
            }
        });
        settingsPanel.add(coverPhotoButton);
        coverPhotoButton.addActionListener(_ -> {
            try {
                changeCoverPhoto(user.getProfile());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Can not change cover photo", "Failed upload", JOptionPane.ERROR_MESSAGE);
            }
        });
        settingsPanel.add(bioButton);
        bioButton.addActionListener(_ -> changeBio(user.getProfile()));

        int result = JOptionPane.showConfirmDialog(this, settingsPanel,
                "Change profile settings", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            JOptionPane.showMessageDialog(this, "Changes applied", "Settings changed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void changeBio(Profile profile) {
        JPanel changeBioPanel = new JPanel(new GridLayout(1, 2));
        changeBioPanel.add(new JLabel("Enter new bio"));
        JTextField newBioField = new JTextField();
        changeBioPanel.add(newBioField);

        int result = JOptionPane.showConfirmDialog(this, changeBioPanel, "Change bio", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            String newBio = newBioField.getText();
            if (newBio.length() > 140){
                JOptionPane.showMessageDialog(this, "Bio should not be longer than 140 characters", "Long bio", JOptionPane.ERROR_MESSAGE);
            }
            else{
                profile.setBio(newBio);
                bioLabel.setText(newBio);
            }
        }
    }

    private void changeCoverPhoto(Profile profile) throws IOException {
        JPanel changeCoverPhotoPanel = new JPanel(new GridLayout(1, 2));
        changeCoverPhotoPanel.add(new JLabel("Choose new cover photo: "));
        JFileChooser coverPhotoChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        coverPhotoChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .png , .jpeg or .jpg", "png", "jpeg", "jpg");
        coverPhotoChooser.addChoosableFileFilter(restrict);

        int result = coverPhotoChooser.showOpenDialog(null);
        String path;
        if (result == JFileChooser.APPROVE_OPTION) {
            path = coverPhotoChooser.getSelectedFile().getAbsolutePath();
            profile.setCoverPhoto(path);
            coverPhotoLabel.setIcon(profile.getCoverPhoto().toIcon());
        }
    }

    private void changeProfilePicture(Profile profile) throws IOException {
        JPanel changeProfilePicturePanel = new JPanel(new GridLayout(1, 2));
        changeProfilePicturePanel.add(new JLabel("Choose new profile picture: "));
        JFileChooser profilePictureChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        profilePictureChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .png , .jpeg or .jpg", "png", "jpeg", "jpg");
        profilePictureChooser.addChoosableFileFilter(restrict);

        int result = profilePictureChooser.showOpenDialog(null);
        String path;
        if (result == JFileChooser.APPROVE_OPTION) {
            path = profilePictureChooser.getSelectedFile().getAbsolutePath();
            profile.setProfilePhoto(path);
            profilePhotoLabel.setIcon(profile.getProfilePhoto().toIcon());
        }
    }

    private void changeDob(User user) {
        JPanel changeDobPanel = new JPanel(new GridLayout(1, 2));
        changeDobPanel.add(new JLabel("Enter date of birth: "));
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date());
        changeDobPanel.add(dateSpinner);

        int result = JOptionPane.showConfirmDialog(this, changeDobPanel, "Change date of birth", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            LocalDate newDateOfBirth = ((Date) dateSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            user.setDateOfBirth(newDateOfBirth);
        }
    }

    private void changePassword(User user) {
        JPanel changePasswordPanel = new JPanel(new GridLayout(2, 2));
        changePasswordPanel.add(new JLabel("Enter new password: "));
        JPasswordField passwordField1 = new JPasswordField();
        changePasswordPanel.add(passwordField1);
        changePasswordPanel.add(new JLabel("Re-enter new password"));
        JPasswordField passwordField2 = new JPasswordField();
        changePasswordPanel.add(passwordField2);

        int result = JOptionPane.showConfirmDialog(this, changePasswordPanel, "Change password", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            String password1 = Arrays.toString(passwordField1.getPassword());
            String password2 = Arrays.toString(passwordField2.getPassword());
            String currentPassword = user.getPassword();
            if (!password1.equals(password2)){
                JOptionPane.showMessageDialog(this, "Passwords don't match", "Unmatched passwords", JOptionPane.ERROR_MESSAGE);
            }
            else if (password1.equals(currentPassword)){
                JOptionPane.showMessageDialog(this, "Password is not changed", "Unchanged Password", JOptionPane.ERROR_MESSAGE);
            }
            else{
                user.setPassword(password1);
                JOptionPane.showMessageDialog(this, "New password set", "Password changed", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void changeEmail(User user) {
        JPanel changeEmailPanel = new JPanel(new GridLayout(1, 2));
        changeEmailPanel.add(new JLabel("Enter new email"));
        JTextField newEmailField = new JTextField();
        changeEmailPanel.add(newEmailField);

        int result = JOptionPane.showConfirmDialog(this, changeEmailPanel, "Change email", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            String newEmail = newEmailField.getText();
            if (!Utilities.validateEmail(newEmail)){
                JOptionPane.showMessageDialog(this, "Email is not valid", "Incorrect username", JOptionPane.ERROR_MESSAGE);
            }
            else{
                user.setEmail(newEmail);
            }
        }
    }

    private void changeUsername(User user) {
        JPanel changeUsernamePanel = new JPanel(new GridLayout(1, 2));
        changeUsernamePanel.add(new JLabel("Enter new username"));
        JTextField newUsernameField = new JTextField();
        changeUsernamePanel.add(newUsernameField);

        int result = JOptionPane.showConfirmDialog(this, changeUsernamePanel, "Change username", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION){
            String newUsername = newUsernameField.getText();
            if (!Utilities.validateUsername(newUsername)){
                JOptionPane.showMessageDialog(this, "Username is not valid", "Incorrect username", JOptionPane.ERROR_MESSAGE);
            }
            else{
                user.setUsername(newUsername);
                usernameLabel.setText(newUsername);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        User user1 = new User("minareda", "minareda03@gmail.com", "mnr12345", LocalDate.of(2003, 3, 17));
        //User user2 = new User("omarHekal", "omarhekal@gmail.com", "oma12345", LocalDate.of(2003, 10, 6));
        UserProfile currentUserProfile = new UserProfile(user1);
        JFrame frame = new JFrame();
        frame.add(currentUserProfile);
        frame.setVisible(true);
    }
}
