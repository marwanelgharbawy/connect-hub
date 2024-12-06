package frontend.newsFeed;

import backend.Database;
import backend.User;
import content.ContentFields;
import content.Story;
import utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

public class StoryCard extends JPanel {
    private final Story story;
    private final User user;

    public StoryCard(Story story) throws IOException {
        this.story = story;
        Database database = Database.getInstance();
        this.user = database.getUser(story.getAuthorId());
        initUI();
    }

    private void initUI() {
        this.setBackground(Utilities.HEX2Color("e8e8e8"));
        this.setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        ImageIcon profileImage = new ImageIcon(user.getProfile().getProfilePhoto().resizeImage(50, 50));
//        ImageIcon profileImage = new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\connect-hub2\\arsenal.png");
        JLabel photoLabel = new JLabel(resizeIcon(profileImage, 50, 50));
        JLabel usernameLabel = new JLabel(user.getUsername());
//        JLabel usernameLabel = new JLabel("Mina Reda");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.setOpaque(false);
        infoPanel.add(photoLabel);
        infoPanel.add(usernameLabel);

        JPanel storyPanel = new JPanel();
        storyPanel.setLayout(new BoxLayout(storyPanel, BoxLayout.Y_AXIS));
        storyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel storyText = new JLabel(story.getContentFields().getText());
        storyText.setFont(new Font("Arial", Font.PLAIN, 16));
        storyText.setForeground(Color.BLACK);
        storyText.setAlignmentX(Component.CENTER_ALIGNMENT);
        storyText.setAlignmentY(Component.CENTER_ALIGNMENT);
        storyPanel.add(storyText);


        if (!story.getContentFields().getImagePath().isEmpty()){
            ImageIcon image = new ImageIcon(story.getContentFields().getImagePath());
            image = resizeIcon(image, 400, 200);
            JLabel imageLabel = new JLabel(image);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            storyPanel.add(imageLabel);
        }

        add(infoPanel, BorderLayout.NORTH);
        add(storyPanel, BorderLayout.CENTER);
    }

    private ImageIcon resizeIcon(ImageIcon originalIcon, int width, int height) {
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static void main(String[] args) throws IOException {
//        User user = new User("minareda", "minareda03@gmail.com", "12345", LocalDate.of(2003, 3, 17));
//        Story story = new Story(user.getUserId(), new ContentFields("Arsenal", ""));
//        JFrame demoStory = new JFrame();
//        StoryCard storyCard = new StoryCard(story);
//        demoStory.add(storyCard);
//        demoStory.setVisible(true);
        ContentFields contentFields = new ContentFields("This is a story text.", "C:\\Users\\ADMIN\\IdeaProjects\\connect-hub2\\arsenal.png");
        Story story = new Story("authorId123", contentFields);

        JFrame frame = new JFrame();
        StoryCard storyCard = new StoryCard(story);
        frame.setContentPane(storyCard);
        frame.pack();
        frame.setVisible(true);
    }


}
