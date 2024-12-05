package frontend;

import content.ContentFields;
import content.Post;
import content.Story;
import utils.UIUtils;

import javax.swing.*;
import java.awt.event.WindowAdapter;

public class ContentCreation extends JFrame {
    private JButton addStoryButton;
    private JPanel mainPanel;
    private JButton addPostButton;
    private final String authorId;

    public ContentCreation(String authorId) {
        UIUtils.initializeWindow(this, mainPanel, "Creating Content", 400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.authorId = authorId;
        // Set up the button action listeners for content creation
        setupButtonListeners();
    }
    // Set up the button action listeners
    private void setupButtonListeners(){
        addStoryButton.addActionListener(_ -> createContent(authorId,true));
        addPostButton.addActionListener(_ -> createContent(authorId,false));
    }
    private void createContent(String authorId, boolean isStory){
        // Create an instance for content fields
        ContentFields contentFields = new ContentFields(null, null);
        // Pass content fields to GetContentFieldsWindow to handle them
        GetContentFieldsWindow getContentFieldsWindow = new GetContentFieldsWindow(contentFields,isStory);
        // Hide content creation window
        setVisible(false);
        // Control the visibility of the current window based on the next window
        getContentFieldsWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                if (contentFields.getText() != null) {
                    if (isStory) {
                        Story story = new Story(authorId, contentFields);
                        // TODO: Add to user and database lists of stories
                    } else {
                        Post post = new Post(authorId, contentFields);
                        // TODO: Add to user and database lists of posts
                    }
                }
                // Show content creation window again
                setVisible(true);
            }
        });
        getContentFieldsWindow.setVisible(true);

    }

    public static void main(String[] args) {
        //TODO: pass user ID when creating an instance of this class
        ContentCreation contentCreation = new ContentCreation("1234");
        contentCreation.setVisible(true);

    }
}
