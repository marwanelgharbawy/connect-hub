package frontend;

import content.ContentFields;
import content.Post;
import content.Story;
import utils.UIUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

public class ContentCreation extends JFrame {
    private JButton addStoryButton;
    private JPanel mainPanel;
    private JButton addPostButton;

    public ContentCreation(String authorId) {
        UIUtils.initializeWindow(this, mainPanel, "Creating Content", 400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createContent(authorId,true);
            }
        });
        addPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createContent(authorId,false);
            }
        });

    }
    private void createContent(String authorId, boolean isStory){
        ContentFields contentFields = new ContentFields(null, null);
        GetContentFieldsWindow getContentFieldsWindow = new GetContentFieldsWindow(contentFields,isStory);
        setVisible(false); // Hide content creation window
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
                setVisible(true); // Show content creation window again
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
