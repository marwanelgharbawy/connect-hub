package frontend.newsFeed;

import backend.Database;
import content.Story;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class StoryNewsFeed extends JPanel {
    
    public StoryNewsFeed() throws IOException {
        initUI();
    }
    
    private void initUI() throws IOException{
        this.setBackground(Color.white);
        JPanel storiesPanel = new JPanel();
        storiesPanel.setBackground(Color.white);
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.Y_AXIS));
        storiesPanel.setBorder(BorderFactory.createEmptyBorder(10, 320, 10, 320));

        ArrayList<Story> stories = Database.getInstance().getCurrentUser().getNewsFeedStories();
        
        if(stories.isEmpty()){
            storiesPanel.add(Box.createRigidArea(new Dimension(0, 200)));
            JLabel no_stories_label = new JLabel("No stories available yet");
            no_stories_label.setFont(new Font("Arial", Font.BOLD, 48));
            no_stories_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            storiesPanel.add(no_stories_label);
        }
        else {
            for(Story story: stories){
                StoryCard storyCard = new StoryCard(story);
                storiesPanel.add(storyCard);
                storiesPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(storiesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}
