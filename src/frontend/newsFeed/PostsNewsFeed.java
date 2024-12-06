package frontend.newsFeed;

import backend.Database;
import content.ContentFields;
import content.Post;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PostsNewsFeed extends JPanel {

    PostsNewsFeed() throws IOException {
        initUI();

    }

    private void initUI() throws IOException {
        this.setBackground(Color.white);
        JPanel postsPanel = new JPanel();
        postsPanel.setBackground(Color.white);
        postsPanel.setLayout(new BoxLayout(postsPanel, BoxLayout.Y_AXIS));
        postsPanel.setBorder(BorderFactory.createEmptyBorder(10, 320, 10, 320));

        ArrayList<Post> posts = Database.getInstance().getCurrentUser().getNewsFeedPosts();

        if(posts.isEmpty()){
            postsPanel.add(Box.createRigidArea(new Dimension(0, 200)));
            JLabel no_posts_label = new JLabel("No posts available yet");
            no_posts_label.setFont(new Font("Arial", Font.BOLD, 48));
            no_posts_label.setAlignmentX(Component.CENTER_ALIGNMENT);
            postsPanel.add(no_posts_label);
        }
        else {
            for(Post post: posts){
                PostCard postCard = new PostCard(post);
                postsPanel.add(postCard);
                postsPanel.add(Box.createRigidArea(new Dimension(0, 30)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(postsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}
