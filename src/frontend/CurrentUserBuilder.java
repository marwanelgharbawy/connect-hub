package frontend;

import backend.Profile;
import backend.User;
import content.ContentFields;
import content.Post;
import content.Story;
import frontend.newsFeed.PostCard;
import utils.Utilities;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class CurrentUserBuilder extends Component {
    private final User currentUser;
    private final UserProfile userProfile;
    private final JPanel mainPanel;
    JPanel postsPanel;
    JLabel coverPhotoLabel;

    public CurrentUserBuilder(UserProfile userProfile, User currentUser) {
        this.currentUser = currentUser;
        this.userProfile = userProfile;
        this.mainPanel = new JPanel(new BorderLayout());
    }


    public JPanel buildCoverPhoto(){
        JPanel coverPhotoPanel = new JPanel(null);
        coverPhotoPanel.setPreferredSize(new Dimension(1000, 350));
        coverPhotoLabel = new JLabel();
        coverPhotoLabel.setBounds(0, 0, 1000, 270);
        coverPhotoLabel.setIcon(new ImageIcon(currentUser.getProfile().getCoverPhoto().toIcon().getImage().getScaledInstance(
                1000, 300, Image.SCALE_DEFAULT)));

        JPanel profilePhotoPanel = new JPanel(null);
        profilePhotoPanel.setBounds(300, 130, 180, 220);
        profilePhotoPanel.setOpaque(false);

        JLabel profilePhotoLabel = new JLabel();
        profilePhotoLabel.setIcon(new ImageIcon(currentUser.getProfile().getProfilePhoto().getImage().getScaledInstance(
                180, 180, Image.SCALE_SMOOTH)));
        profilePhotoLabel.setBounds(0, 0, 180, 180);
        profilePhotoLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

        JLabel usernameLabel = new JLabel(currentUser.getUsername(), SwingConstants.CENTER);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setBounds(0, 180, 180, 20);

        userProfile.coverPhotoLabel = coverPhotoLabel;
        userProfile.profilePhotoLabel = profilePhotoLabel;
        userProfile.usernameLabel = usernameLabel;

        profilePhotoPanel.add(profilePhotoLabel);
        profilePhotoPanel.add(usernameLabel);

        coverPhotoPanel.add(profilePhotoPanel);
        coverPhotoPanel.add(coverPhotoLabel);

        return coverPhotoPanel;
    }

    public JPanel buildBioPanel() {
        JPanel bioPanel = new JPanel(new BorderLayout());
        bioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel bioLabel = new JLabel(currentUser.getProfile().getBio());
        bioLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        bioLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JButton settingsButton = new JButton("Settings");
        settingsButton.setPreferredSize(new Dimension(100, 30));
        settingsButton.addActionListener(_ -> showProfileSettings(currentUser));

        userProfile.bioPanel = bioPanel;

        bioPanel.add(bioLabel, BorderLayout.CENTER);
        bioPanel.add(settingsButton, BorderLayout.EAST);

        userProfile.bioLabel = bioLabel;

        return bioPanel;
    }

    private void showProfileSettings(User currentUser){
        JPanel settingsPanel = new JPanel(new GridLayout(7, 1));
        JButton usernameButton = new JButton("Change username");
        JButton emailButton = new JButton("Change email");
        JButton passwordButton = new JButton("Change password");
        JButton dobButton = new JButton("Change date of birth");
        JButton profilePictureButton = new JButton("Change profile picture");
        JButton coverPhotoButton = new JButton("Change cover photo");
        JButton bioButton = new JButton("Change bio");
        usernameButton.addActionListener(_ -> changeUsername(currentUser));
        settingsPanel.add(emailButton);
        emailButton.addActionListener(_ -> changeEmail(currentUser));
        settingsPanel.add(passwordButton);
        passwordButton.addActionListener(_ -> changePassword(currentUser));
        settingsPanel.add(dobButton);
        dobButton.addActionListener(_ -> changeDob(currentUser));
        settingsPanel.add(profilePictureButton);
        profilePictureButton.addActionListener(_ -> {
            try {
                changeProfilePicture(currentUser.getProfile());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Can not change profile picture", "Failed upload", JOptionPane.ERROR_MESSAGE);
            }
        });
        settingsPanel.add(coverPhotoButton);
        coverPhotoButton.addActionListener(_ -> {
            try {
                changeCoverPhoto(currentUser.getProfile());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Can not change cover photo", "Failed upload", JOptionPane.ERROR_MESSAGE);
            }
        });
        settingsPanel.add(bioButton);
        bioButton.addActionListener(_ -> changeBio(currentUser.getProfile()));

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
                userProfile.setBioLabel(newBio);
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
            userProfile.setCoverPhotoLabel(new ImageIcon(currentUser.getProfile().getCoverPhoto().toIcon().getImage().getScaledInstance(
                    1000, 300, Image.SCALE_DEFAULT)));
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
            userProfile.setProfilePhotoLabel(new ImageIcon(currentUser.getProfile().getProfilePhoto().getImage().getScaledInstance(
                    180, 180, Image.SCALE_SMOOTH)));
            populatePostPanel();
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
                userProfile.usernameLabel.setText(newUsername);
            }
        }
    }

    public JPanel buildPostsPanel() throws IOException {
        postsPanel = new JPanel();
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        populatePostPanel();
        return postsPanel;
    }

    public void populatePostPanel() throws IOException {
        postsPanel.removeAll();
        postsPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));
        ArrayList<Post> posts = currentUser.getContentManager().getPosts();
        if(posts.isEmpty()){
            postsPanel.add(Box.createRigidArea(new Dimension(0, 200)));
            JLabel no_posts_label = new JLabel("No posts available yet");
            no_posts_label.setFont(new Font("Arial", Font.BOLD, 48));
            no_posts_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            postsPanel.add(no_posts_label);
        }
        else {
            posts.sort(Comparator.comparing(Post::getTimestamp).reversed());
            for(Post post: posts){
                PostCard postCard = new PostCard(post);
                postsPanel.add(postCard);
                postsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            }
        }
    }

    public JPanel build() throws IOException {
        mainPanel.add(buildCoverPhoto(), BorderLayout.NORTH);
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(buildBioPanel(), BorderLayout.NORTH);
        mainContentPanel.add(buildPostsPanel(), BorderLayout.CENTER);
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);

        return mainPanel;
    }
}