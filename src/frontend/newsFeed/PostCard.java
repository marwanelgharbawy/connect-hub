package frontend.newsFeed;

import backend.Database;
import backend.User;
import content.ContentFields;
import content.Post;
import utils.Utilities;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PostCard extends JPanel{
    private Post post;
    private User selected_user;

    public PostCard(Post post) throws IOException {
        this.post = post;
//        selected_user = Database.getInstance().getUser(post.getAuthorId());
        initUI();
    }

    private void initUI(){
        this.setBackground(Utilities.HEX2Color("b8b8b8"));
        this.setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        /* user info*/
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
//        ImageIcon profileImage = resizeIcon(selected_user.getProfile().getProfilePhoto().toIcon(), 50, 50);
        ImageIcon profileImage = resizeIcon(new ImageIcon("C:/Users/Omar Hekal/Desktop/profiel.jpg"), 50, 50);
        JLabel photoLabel = new JLabel(profileImage);
//        JLabel usernameLabel = new JLabel(selected_user.getUsername());
        JLabel usernameLabel = new JLabel("Mina Reda");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(photoLabel);
        infoPanel.add(usernameLabel);


        /* post text*/
        JTextArea postTextArea = new JTextArea(post.getContentFields().getText());
//        postTextArea.setWrapStyleWord();
        postTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        postTextArea.setLineWrap(true);
        postTextArea.setWrapStyleWord(true);
        postTextArea.setEditable(false);
        postTextArea.setBackground(new Color(240, 240, 240));
        postTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane textScrollPanel = new JScrollPane(postTextArea);

        add(infoPanel, BorderLayout.NORTH);
        add(textScrollPanel, BorderLayout.CENTER);

        if(!post.getContentFields().getImagePath().equals("")){
            /* post image*/
            ImageIcon postImage = new ImageIcon(post.getContentFields().getImagePath());
            postImage = resizeIcon(postImage, 400, 200);
            JLabel postImageLabel = new JLabel(postImage);
            postImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            add(postImageLabel, BorderLayout.SOUTH);
            this.setPreferredSize(new Dimension(400, 400));
        }
        else{
            this.setPreferredSize(new Dimension(400, 300));
        }
    }


    private ImageIcon resizeIcon(ImageIcon originalIcon, int width, int height){
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        return scaledIcon;
    }

    public static void main(String[] args) throws IOException {
        ContentFields contentFields = new ContentFields("hello omar how you doing\nhbfhdsfh\njbskafd\njfjbgerbg\sbkgbsjfg\nbgfhkbsfd", "C:/Users/Omar Hekal/Desktop/img.jpg");
        Post post1 = new Post("fdsdfs", contentFields);

        JFrame frame = new JFrame();
        PostCard postCard = new PostCard(post1);
        frame.setContentPane(postCard);
        frame.pack();
//        frame.setPreferredSize(new Dimension(400, 600));
        frame.setVisible(true);
    }
}
