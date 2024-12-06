package frontend;

import backend.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

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

        CurrentUserBuilder builder = new CurrentUserBuilder(this, user);
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

    public static void main(String[] args) throws IOException {
        User user1 = new User("minareda", "minareda03@gmail.com", "mnr12345", LocalDate.of(2003, 3, 17));
        //User user2 = new User("omarHekal", "omarhekal@gmail.com", "oma12345", LocalDate.of(2003, 10, 6));
        UserProfile currentUserProfile = new UserProfile(user1);
        JFrame frame = new JFrame();
        frame.add(currentUserProfile);
        frame.setVisible(true);
    }

    public JLabel getBioLabel() {
        return bioLabel;
    }

    public JLabel getCoverPhotoLabel() {
        return coverPhotoLabel;
    }

    public JLabel getProfilePhotoLabel() {
        return profilePhotoLabel;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public void setBioLabel(String newLabel) {
        this.bioLabel.setText(newLabel);
    }

    public void setCoverPhotoLabel(ImageIcon newCoverPhoto) {
        this.coverPhotoLabel.setIcon(newCoverPhoto);
    }

    public void setProfilePhotoLabel(ImageIcon newProfilePhoto) {
        this.profilePhotoLabel.setIcon(newProfilePhoto);
    }

    public void setUsernameLabel(String newUsername) {
        this.usernameLabel.setText(newUsername);
    }
}