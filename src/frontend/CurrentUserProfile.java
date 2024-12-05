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
import java.util.Calendar;
import java.util.Date;

public class CurrentUserProfile extends JFrame {

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

    public CurrentUserProfile(Profile profile) throws IOException {
        // Window setup
        setTitle(profile.getUser().getUsername() + "'s Profile");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Cover photo panel
        coverPhotoPanel = new JPanel();
        coverPhotoPanel.setLayout(null); // Absolute layout for custom positioning
        coverPhotoPanel.setPreferredSize(new Dimension(1000, 350));
        coverPhotoLabel = new JLabel();
        coverPhotoLabel.setBounds(0, 0, 1000, 270);
        coverPhotoLabel.setIcon(new ImageIcon(profile.getCoverPhoto().toIcon().getImage().getScaledInstance(
                1000, 300, Image.SCALE_DEFAULT)));

        profilePhotoPanel = new JPanel(null);
        profilePhotoPanel.setBounds(300, 130, 180, 220);
        profilePhotoPanel.setOpaque(false);

        profilePhotoLabel = new JLabel();
        profilePhotoLabel.setIcon(new ImageIcon(profile.getProfilePhoto().getImage().getScaledInstance(
                180, 180, Image.SCALE_SMOOTH)));
        profilePhotoLabel.setBounds(0, 0, 180, 180);
        profilePhotoLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

        usernameLabel = new JLabel(profile.getUser().getUsername(), SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setBounds(0, 180, 180, 20);

        profilePhotoPanel.add(profilePhotoLabel);
        profilePhotoPanel.add(usernameLabel);

        coverPhotoPanel.add(profilePhotoPanel);
        coverPhotoPanel.add(coverPhotoLabel);

        add(coverPhotoPanel, BorderLayout.NORTH);

        // Main content panel
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BorderLayout());

        bioPanel = new JPanel(new BorderLayout());
        bioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bioLabel = new JLabel(profile.getBio());
        bioLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        bioLabel.setHorizontalAlignment(SwingConstants.LEFT);

        settingsButton = new JButton("Settings");
        settingsButton.setPreferredSize(new Dimension(100, 30));
        settingsButton.addActionListener(_ -> showProfileSettings(profile));

        bioPanel.add(bioLabel, BorderLayout.CENTER);
        bioPanel.add(settingsButton, BorderLayout.EAST);

        mainContentPanel.add(bioPanel, BorderLayout.NORTH);

        // Add dummy "Posts" section
        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));
        for (int i = 1; i <= 100; i++) {
//            JLabel post = new JLabel("Post " + i + ": This is a dummy post.");
//            post.setFont(new Font("Arial", Font.PLAIN, 14));
//            post.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//            post.setBackground(Color.LIGHT_GRAY);
//            post.setOpaque(true);
//            post.setAlignmentX(Component.CENTER_ALIGNMENT);
            ContentFields contentFields = new ContentFields("hello omar how you doing\nhbfhdsfh\njbskafd\njfjbgerbg\sbkgbsjfg\nbgfhkbsfd", "C:/Users/Omar Hekal/Desktop/img.jpg");
            Post post1 = new Post("fdsdfs", contentFields);

            JFrame frame = new JFrame();
            PostCard postCard = new PostCard(post1);
            postsPanel.add(postCard);
            postsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        }

        scrollPane = new JScrollPane(postsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainContentPanel.add(scrollPane, BorderLayout.CENTER);

        // Add main content panel to the frame
        add(mainContentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void showProfileSettings(Profile profile) {
        JPanel settingsPanel = new JPanel(new GridLayout(7, 1));
        JButton usernameButton = new JButton("Change username");
        JButton emailButton = new JButton("Change email");
        JButton passwordButton = new JButton("Change password");
        JButton dobButton = new JButton("Change date of birth");
        JButton profilePictureButton = new JButton("Change profile picture");
        JButton coverPhotoButton = new JButton("Change cover photo");
        JButton bioButton = new JButton("Change bio");
        usernameButton.addActionListener(_ -> changeUsername(profile));
        settingsPanel.add(emailButton);
        emailButton.addActionListener(_ -> changeEmail(profile));
        settingsPanel.add(passwordButton);
        passwordButton.addActionListener(_ -> changePassword(profile));
        settingsPanel.add(dobButton);
        dobButton.addActionListener(_ -> changeDob(profile));
        settingsPanel.add(profilePictureButton);
        profilePictureButton.addActionListener(_ -> {
            try {
                changeProfilePicture(profile);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Can not change profile picture", "Failed upload", JOptionPane.ERROR_MESSAGE);
            }
        });
        settingsPanel.add(coverPhotoButton);
        coverPhotoButton.addActionListener(_ -> {
            try {
                changeCoverPhoto(profile);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Can not change cover photo", "Failed upload", JOptionPane.ERROR_MESSAGE);
            }
        });
        settingsPanel.add(bioButton);
        bioButton.addActionListener(_ -> changeBio(profile));

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

    private void changeDob(Profile profile) {
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
            profile.getUser().setDateOfBirth(newDateOfBirth);
        }
    }

    private void changePassword(Profile profile) {
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
            String currentPassword = profile.getUser().getPassword();
            if (!password1.equals(password2)){
                JOptionPane.showMessageDialog(this, "Passwords don't match", "Unmatched passwords", JOptionPane.ERROR_MESSAGE);
            }
            else if (password1.equals(currentPassword)){
                JOptionPane.showMessageDialog(this, "Password is not changed", "Unchanged Password", JOptionPane.ERROR_MESSAGE);
            }
            else{
                profile.getUser().setPassword(password1);
                JOptionPane.showMessageDialog(this, "New password set", "Password changed", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void changeEmail(Profile profile) {
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
                profile.getUser().setEmail(newEmail);
            }
        }
    }

    private void changeUsername(Profile profile) {
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
                profile.getUser().setUsername(newUsername);
                usernameLabel.setText(newUsername);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        User user = new User("minareda", "minareda03@gmail.com", "mnr115279", LocalDate.of(2003, 3, 17));
        Profile profile = new Profile(user, "", "", "");
        profile.setBio("Co-founder Connect-Hub");
        new CurrentUserProfile(profile);
    }
}
